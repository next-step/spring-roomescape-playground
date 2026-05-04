package roomescape.view;

public enum ViewNames {
    HOME("home"),
    RESERVATION("reservation");

    private final String viewName;

    ViewNames(String viewName) {
       this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }
}
