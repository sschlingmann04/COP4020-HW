package plc.project;

public final class ParseException extends RuntimeException {

    private final int index;

    public ParseException(String message, int index) {
        super(message);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    // Adding an equals method to aid in testing ParseExceptions
    @Override
    public boolean equals(Object obj) {
        return obj instanceof ParseException && index == ((ParseException) obj).index;
    }

}
