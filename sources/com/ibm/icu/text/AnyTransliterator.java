package com.ibm.icu.text;

import com.ibm.icu.lang.UScript;
import com.ibm.icu.text.Transliterator;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

class AnyTransliterator extends Transliterator {
    static final String ANY = "Any";
    static final String LATIN_PIVOT = "-Latin;Latin-";
    static final String NULL_ID = "Null";
    static final char TARGET_SEP = '-';
    static final char VARIANT_SEP = '/';
    private ConcurrentHashMap<Integer, Transliterator> cache;
    private String target;
    private int targetScript;
    private Transliterator widthFix = Transliterator.getInstance("[[:dt=Nar:][:dt=Wide:]] nfkd");

    /* access modifiers changed from: protected */
    public void handleTransliterate(Replaceable text, Transliterator.Position pos, boolean isIncremental) {
        int allStart = pos.start;
        int allLimit = pos.limit;
        ScriptRunIterator it = new ScriptRunIterator(text, pos.contextStart, pos.contextLimit);
        while (it.next()) {
            if (it.limit > allStart) {
                Transliterator t = getTransliterator(it.scriptCode);
                if (t == null) {
                    pos.start = it.limit;
                } else {
                    boolean incremental = isIncremental && it.limit >= allLimit;
                    pos.start = Math.max(allStart, it.start);
                    pos.limit = Math.min(allLimit, it.limit);
                    int limit = pos.limit;
                    t.filteredTransliterate(text, pos, incremental);
                    int delta = pos.limit - limit;
                    allLimit += delta;
                    it.adjustLimit(delta);
                    if (it.limit >= allLimit) {
                        break;
                    }
                }
            }
        }
        pos.limit = allLimit;
    }

    private AnyTransliterator(String id, String theTarget, String theVariant, int theTargetScript) {
        super(id, (UnicodeFilter) null);
        this.targetScript = theTargetScript;
        this.cache = new ConcurrentHashMap<>();
        this.target = theTarget;
        if (theVariant.length() > 0) {
            this.target = theTarget + VARIANT_SEP + theVariant;
        }
    }

    public AnyTransliterator(String id, UnicodeFilter filter, String target2, int targetScript2, Transliterator widthFix2, ConcurrentHashMap<Integer, Transliterator> cache2) {
        super(id, filter);
        this.targetScript = targetScript2;
        this.cache = cache2;
        this.target = target2;
    }

    private Transliterator getTransliterator(int source) {
        int i = this.targetScript;
        if (source != i && source != -1) {
            Integer key = Integer.valueOf(source);
            Transliterator t = this.cache.get(key);
            if (t != null) {
                return t;
            }
            String sourceName = UScript.getName(source);
            try {
                t = Transliterator.getInstance(sourceName + TARGET_SEP + this.target, 0);
            } catch (RuntimeException e) {
            }
            if (t == null) {
                try {
                    t = Transliterator.getInstance(sourceName + LATIN_PIVOT + this.target, 0);
                } catch (RuntimeException e2) {
                }
            }
            if (t != null) {
                if (!isWide(this.targetScript)) {
                    List<Transliterator> v = new ArrayList<>();
                    v.add(this.widthFix);
                    v.add(t);
                    t = new CompoundTransliterator(v);
                }
                Transliterator prevCachedT = this.cache.putIfAbsent(key, t);
                if (prevCachedT != null) {
                    return prevCachedT;
                }
                return t;
            } else if (!isWide(this.targetScript)) {
                return this.widthFix;
            } else {
                return t;
            }
        } else if (isWide(i)) {
            return null;
        } else {
            return this.widthFix;
        }
    }

    private boolean isWide(int script) {
        return script == 5 || script == 17 || script == 18 || script == 20 || script == 22;
    }

    static void register() {
        HashMap<String, Set<String>> seen = new HashMap<>();
        Enumeration<String> s = Transliterator.getAvailableSources();
        while (s.hasMoreElements()) {
            String source = s.nextElement();
            if (!source.equalsIgnoreCase(ANY)) {
                Enumeration<String> t = Transliterator.getAvailableTargets(source);
                while (t.hasMoreElements()) {
                    String target2 = t.nextElement();
                    int targetScript2 = scriptNameToCode(target2);
                    if (targetScript2 != -1) {
                        Set<String> seenVariants = seen.get(target2);
                        if (seenVariants == null) {
                            Set<String> hashSet = new HashSet<>();
                            seenVariants = hashSet;
                            seen.put(target2, hashSet);
                        }
                        Enumeration<String> v = Transliterator.getAvailableVariants(source, target2);
                        while (v.hasMoreElements()) {
                            String variant = v.nextElement();
                            if (!seenVariants.contains(variant)) {
                                seenVariants.add(variant);
                                Transliterator.registerInstance(new AnyTransliterator(TransliteratorIDParser.STVtoID(ANY, target2, variant), target2, variant, targetScript2));
                                Transliterator.registerSpecialInverse(target2, NULL_ID, false);
                            }
                        }
                    }
                }
            }
        }
    }

    private static int scriptNameToCode(String name) {
        try {
            int[] codes = UScript.getCode(name);
            if (codes != null) {
                return codes[0];
            }
            return -1;
        } catch (MissingResourceException e) {
            return -1;
        }
    }

    private static class ScriptRunIterator {
        public int limit;
        public int scriptCode;
        public int start;
        private Replaceable text;
        private int textLimit;
        private int textStart;

        public ScriptRunIterator(Replaceable text2, int start2, int limit2) {
            this.text = text2;
            this.textStart = start2;
            this.textLimit = limit2;
            this.limit = start2;
        }

        public boolean next() {
            int s;
            this.scriptCode = -1;
            int i = this.limit;
            this.start = i;
            if (i == this.textLimit) {
                return false;
            }
            while (true) {
                int i2 = this.start;
                if (i2 > this.textStart && ((s = UScript.getScript(this.text.char32At(i2 - 1))) == 0 || s == 1)) {
                    this.start--;
                }
            }
            while (true) {
                int ch = this.limit;
                if (ch >= this.textLimit) {
                    break;
                }
                int s2 = UScript.getScript(this.text.char32At(ch));
                if (!(s2 == 0 || s2 == 1)) {
                    int i3 = this.scriptCode;
                    if (i3 == -1) {
                        this.scriptCode = s2;
                    } else if (s2 != i3) {
                        break;
                    }
                }
                this.limit++;
            }
            return true;
        }

        public void adjustLimit(int delta) {
            this.limit += delta;
            this.textLimit += delta;
        }
    }

    public Transliterator safeClone() {
        UnicodeFilter filter = getFilter();
        if (filter != null && (filter instanceof UnicodeSet)) {
            filter = new UnicodeSet((UnicodeSet) filter);
        }
        return new AnyTransliterator(getID(), filter, this.target, this.targetScript, this.widthFix, this.cache);
    }

    public void addSourceTargetSet(UnicodeSet inputFilter, UnicodeSet sourceSet, UnicodeSet targetSet) {
        UnicodeSet myFilter = getFilterAsUnicodeSet(inputFilter);
        sourceSet.addAll(myFilter);
        if (myFilter.size() != 0) {
            targetSet.addAll(0, 1114111);
        }
    }
}
