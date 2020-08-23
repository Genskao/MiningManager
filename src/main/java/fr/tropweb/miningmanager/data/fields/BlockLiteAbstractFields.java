package fr.tropweb.miningmanager.data.fields;

public enum BlockLiteAbstractFields implements AbstractFields {
    X("x", 1),
    Y("y", 2),
    Z("z", 3),
    WORLD("world", 4),
    MATERIAL("material", 5),
    PLACED("placed", 6),
    BLOCKED("blocked", 7);

    private final String fieldName;
    private final int fieldId;

    BlockLiteAbstractFields(final String fieldName, final int fieldId) {
        this.fieldName = fieldName;
        this.fieldId = fieldId;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public int getFieldId() {
        return fieldId;
    }
}