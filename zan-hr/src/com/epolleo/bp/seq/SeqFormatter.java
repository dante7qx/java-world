/*-
 * Copyright (c) 2007-2008
 * All rights reserved. 
 */

package com.epolleo.bp.seq;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 表达式 %v{5} 用来输出当前序号。其中{5}内为位数，为可变参数。 如尾数不足5位，则补0. 超出5位则取前5位
 * <p>
 * 如果当前序号为1203， 格式为:%v{5}，输出为：12030 格式为:%v{2}，输出为：12
 * <p>
 * %d{yyyyMMdd} 用来定义时间格式。其中{yyyyMMdd}的格式可以自定义。 默认new Date(yyyyMMdd)
 * <p>
 * %1{3}用来定义自定义参数，表示第一个参数的后三位字符串，不足则在前方补0。
 */

public final class SeqFormatter {

    static final Logger log = LoggerFactory.getLogger(SeqFormatter.class);

    static final Map<String, SeqFormatter> cache = new LinkedHashMap<String, SeqFormatter>() {

        /**
         * serialVersionUID
         */
        private static final long serialVersionUID = 866105152372213700L;

        protected boolean removeEldestEntry(Map.Entry<String, SeqFormatter> eldest) {
            return size() < 1024;
        }
    };

    public static synchronized SeqFormatter getSeqFormatter(String pattern) {
        SeqFormatter formatter = cache.get(pattern);
        if (formatter == null) {
            formatter = new SeqFormatter(pattern);
            cache.put(pattern, formatter);
        }
        return formatter;
    }

    Converter head = null;

    String pattern;

    private SeqFormatter(String pattern) {
        if (pattern == null) {
            pattern = "%v";
        }
        this.pattern = pattern;
        head = new Parser(pattern).parse();
    }

    public synchronized String format(long value, Date date, Object... message) {
        SeqEvent event = new SeqEvent(value, date, message);
        Converter c = head;
        while (c != null) {
            c.format(event);
            c = c.next;
        }
        return event.buf().toString();
    }

    static class SeqEvent {
        private final Date timeStamp;
        private final long id;
        private Object[] args;
        private final StringBuffer buf = new StringBuffer();

        public SeqEvent(long id, Date date, Object... args) {
            this.args = args;
            this.id = id;
            timeStamp = date;
        }

        public Object[] getArgs() {
            return args;
        }

        public Date getTimeStamp() {
            return timeStamp;
        }

        public StringBuffer buf() {
            return buf;
        }
    }

    static class Parser {

        static final char ESCAPE_CHAR = '%';

        static final int LITERAL_STATE = 0;

        static final int CONVERTER_STATE = 1;

        int state;

        protected StringBuffer currentLiteral = new StringBuffer(32);

        protected int i;

        Converter head;

        Converter tail;

        protected String pattern;

        public Parser(String pattern) {
            this.pattern = pattern;
            state = LITERAL_STATE;
        }

        private void addToList(Converter pc) {
            if (head == null) {
                head = tail = pc;
            } else {
                tail.next = pc;
                tail = pc;
            }
        }

        protected String extractOption() {
            if ((i < pattern.length()) && (pattern.charAt(i) == '{')) {
                int end = pattern.indexOf('}', i);
                if (end > i) {
                    String r = pattern.substring(i + 1, end);
                    i = end + 1;
                    return r;
                }
            }
            return null;
        }

        int extractPrecisionOption() {
            String opt = extractOption();
            int r = 0;
            if (opt != null) {
                try {
                    r = Integer.parseInt(opt);
                    if (r <= 0) {
                        log.error("Precision option (" + opt + ") isn't a positive integer.");
                        r = 0;
                    }
                } catch (NumberFormatException e) {
                    log.error("Category option \"" + opt + "\" not a decimal integer.", e);
                }
            }
            return r;
        }

        public Converter parse() {
            char c;
            i = 0;
            while (i < pattern.length()) {
                c = pattern.charAt(i++);
                switch (state) {
                case LITERAL_STATE:
                    // In literal state, the last char is always a literal.
                    if (i == pattern.length()) {
                        currentLiteral.append(c);
                        continue;
                    }
                    if (c == ESCAPE_CHAR) {
                        // peek at the next char.
                        switch (pattern.charAt(i)) {
                        case ESCAPE_CHAR:
                            currentLiteral.append(c);
                            i++; // move pointer
                            break;
                        default:
                            if (currentLiteral.length() != 0) {
                                addToList(new LiteralConverter(currentLiteral.toString()));
                            }
                            currentLiteral.setLength(0);
                            currentLiteral.append(c); // append %
                            state = CONVERTER_STATE;
                        }
                    } else {
                        currentLiteral.append(c);
                    }
                    break;
                case CONVERTER_STATE:
                    currentLiteral.append(c);
                    finalizeConverter(c);
                    break;
                } // switch
            } // while
            if (currentLiteral.length() != 0) {
                addToList(new LiteralConverter(currentLiteral.toString()));
            }
            return head;
        }

        protected void finalizeConverter(char c) {
            Converter pc = null;
            String dOpt = "";
            switch (c) {
            case 'd':
                String dateFormatStr = "yyyyMMdd";
                DateFormat df;
                dOpt = extractOption();
                if (dOpt != null) {
                    dateFormatStr = dOpt;
                }
                try {
                    df = new SimpleDateFormat(dateFormatStr);
                } catch (IllegalArgumentException e) {
                    log.error("Could not instantiate SimpleDateFormat with " + dateFormatStr, e);
                    df = new SimpleDateFormat("yyyyMMdd");
                }
                pc = new DateConverter(df);
                currentLiteral.setLength(0);
                break;
            case 'v':
                dOpt = extractOption();
                if (dOpt != null) {
                    pc = new IdConverter(Integer.parseInt(dOpt));
                } else {
                    pc = new IdConverter(-1);
                }
                currentLiteral.setLength(0);
                break;
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                dOpt = extractOption();
                if (dOpt != null) {
                    pc = new ArgConverter(c - '1', Integer.parseInt(dOpt));
                } else {
                    pc = new ArgConverter(c - '1', -1);
                }
                currentLiteral.setLength(0);
                break;

            default:
                log.error("Unexpected char [" + c + "] at position " + i + " in conversion patterrn.");
                pc = new LiteralConverter(currentLiteral.toString());
                currentLiteral.setLength(0);
            }

            addConverter(pc);
        }

        protected void addConverter(Converter pc) {
            currentLiteral.setLength(0);
            // Add the pattern converter to the list.
            addToList(pc);
            // Next pattern is assumed to be a literal.
            state = LITERAL_STATE;
        }

    }

    abstract static class Converter {

        public Converter next;

        protected Converter() {
        }

        public void format(SeqEvent event) {
        }

    }

    static class ArgConverter extends Converter {
        final int idx;
        int width;

        ArgConverter(int idx, int width) {
            this.idx = idx;
            this.width = width;
        }

        public void format(SeqEvent e) {
            Object[] args = e.getArgs();
            String v = "";
            if (args != null && args.length > idx) {
                v = String.valueOf(args[idx]);
                // e.buf().append(args[idx]);
            }
            if (width < 1) {
                e.buf().append(v);
            } else {
                if (v.length() >= width) {
                    e.buf().append(v.substring(v.length() - width));
                } else {
                    int len = width - v.length();
                    for (int i = 0; i < len; i++) {
                        e.buf().append('0');
                    }
                    e.buf().append(v);
                }
            }
        }
    }

    static class IdConverter extends Converter {
        int width;

        IdConverter(int width) {
            this.width = width;
        }

        public void format(SeqEvent e) {
            String id = Long.toString(e.id);
            if (width < 1) {
                e.buf().append(id);
            } else {
                if (id.length() >= width) {
                    e.buf().append(id.substring(id.length() - width));
                } else {
                    int len = width - id.length();
                    for (int i = 0; i < len; i++) {
                        e.buf().append('0');
                    }
                    e.buf().append(id);
                }
            }
        }
    }

    static class LiteralConverter extends Converter {
        String literal;

        LiteralConverter(String value) {
            literal = value;
        }

        public void format(SeqEvent e) {
            e.buf().append(literal);
        }
    }

    static class DateConverter extends Converter {
        DateFormat df;
        int msite;
        int esite;

        DateConverter(DateFormat df) {
            this.df = df;
        }

        public void format(SeqEvent e) {
            synchronized (df) {
                e.buf().append(df.format(e.timeStamp));
            }
        }
    }

    public static void main(String[] args) {
        SeqFormatter sf = new SeqFormatter("%d%v-%1");
        System.out.println(sf.format(199, new Date(), "arg1"));
        sf = new SeqFormatter("%d{MMdd}%v{4}-%1");
        System.out.println(sf.format(199, new Date(), "arg1"));
        sf = new SeqFormatter("%d{MMdd}%v{2}-%1{9}");
        System.out.println(sf.format(199, new Date(), "arg1"));
        sf = new SeqFormatter("%1{3}U%d-%v{4}");
        System.out.println(sf.format(35461, new Date(), 1));
    }
}
