package com.epolleo.bp.seq.bean;

public class SeqBean {
    private String id;
    public String seqKey;
    public String name;
    public long value;
    public int fetchSize;
    public String formatStr;

    public String getSeqKey() {
        return seqKey;
    }

    public void setSeqKey(String seqKey) {
        this.seqKey = seqKey;
        this.id = seqKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public int getFetchSize() {
        return fetchSize;
    }

    public void setFetchSize(int fetchSize) {
        this.fetchSize = fetchSize;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SeqBean [id=" + id + ", seqKey=" + seqKey + ", name=" + name
            + ", value=" + value + ", formatStr=" + formatStr + ", fetchSize="
            + fetchSize + "]";
    }

    public String getExpression() {
        return formatStr;
    }

    public void setExpression(String expression) {
        this.formatStr = expression;
    }

}
