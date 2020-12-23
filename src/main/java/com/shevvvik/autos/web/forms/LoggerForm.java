package com.shevvvik.autos.web.forms;

public class LoggerForm {

    private boolean defaultOp;
    private boolean searchOp;
    private boolean statusOp;
    private boolean assignOp;
    private boolean logInOp;
    private boolean registrationOp;
    private boolean createOp;

    private String dateStart;
    private String dateEnd;

    public boolean isDefaultOp() {
        return defaultOp;
    }

    public void setDefaultOp(boolean defaultOp) {
        this.defaultOp = defaultOp;
    }

    public boolean isSearchOp() {
        return searchOp;
    }

    public void setSearchOp(boolean searchOp) {
        this.searchOp = searchOp;
    }

    public boolean isStatusOp() {
        return statusOp;
    }

    public void setStatusOp(boolean statusOp) {
        this.statusOp = statusOp;
    }

    public boolean isAssignOp() {
        return assignOp;
    }

    public void setAssignOp(boolean assignOp) {
        this.assignOp = assignOp;
    }

    public boolean isLogInOp() {
        return logInOp;
    }

    public void setLogInOp(boolean logInOp) {
        this.logInOp = logInOp;
    }

    public boolean isRegistrationOp() {
        return registrationOp;
    }

    public void setRegistrationOp(boolean registrationOp) {
        this.registrationOp = registrationOp;
    }

    public boolean isCreateOp() {
        return createOp;
    }

    public void setCreateOp(boolean createOp) {
        this.createOp = createOp;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }
}
