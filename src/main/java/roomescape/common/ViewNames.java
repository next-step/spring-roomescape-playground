package roomescape.common;

public enum ViewNames {
    HOME("home"),
    RESERVATION("new-reservation");

    private final String viewName;

    ViewNames(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }
}
