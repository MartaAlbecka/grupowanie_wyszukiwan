package webAPI.models;

public class SearchForm {

    private String value;
    private String similarity;

    public String getValue() {
        return value;
    }

    public SearchForm() {
    }

    public SearchForm(String value, String similarity) {
        this.value = value;
        this.similarity = similarity;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSimilarity() {
        return similarity;
    }

    public void setSimilarity(String similarity) {
        this.similarity = similarity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SearchForm that = (SearchForm) o;

        if (getValue() != null ? !getValue().equals(that.getValue()) : that.getValue() != null) return false;
        return getSimilarity() != null ? getSimilarity().equals(that.getSimilarity()) : that.getSimilarity() == null;
    }

    @Override
    public int hashCode() {
        int result = getValue() != null ? getValue().hashCode() : 0;
        result = 31 * result + (getSimilarity() != null ? getSimilarity().hashCode() : 0);
        return result;
    }
}