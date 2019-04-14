package softuni.aggregator.constants;

public final class ErrorMessages {

    public static final String INVALID_PASSWORD = "Invalid password!";
    public static final String INVALID_OLD_PASSWORD = "Invalid old password!";
    public static final String INVALID_NEW_PASSWORD = "Invalid new password!";
    public static final String INVALID_EMAIL = "Invalid email!";
    public static final String INVALID_FIRST_NAME = "Invalid first name!";
    public static final String INVALID_LAST_NAME = "Invalid last name!";
    public static final String PASSWORD_DONT_MATCH = "Passwords do not match";
    public static final String WRONG_PASSWORD = "Passwords do not match";

    public static final String EMAIL_ALREADY_TAKEN = "This email is already taken.";
    public static final String WRONG_EXPORT_TYPE = "Wrong export type selected.";
    public static final String NO_ITEMS_SELECTED = "No items selected.";
    public static final String EXPORT_FAILED = "Export failed.";
    public static final String EXCEL_WRITING_FAILED = "Failed to write excel file!";

    public static final String COMPANY_NOT_FOUND = "No such company.";
    public static final String EMPLOYEE_NOT_FOUND = "No such company.";
    public static final String EXPORT_NOT_FOUND = "No such company.";
    public static final String INDUSTRY_NOT_FOUND = "No such industry.";
    public static final String ROLE_NOT_FOUND = "No such role.";
    public static final String USER_NOT_FOUND = "No such user.";

    public static final String ROOT_ADMIN_SUSPEND_FORBIDDEN = "You cannot suspend the ROOT ADMIN.";
    public static final String ROOT_ADMIN_ROLE_CHANGE_FORBIDDEN = "You cannot change the ROOT ADMIN's role.";
    public static final String ROOT_ADMIN_ROLE_ASSIGN_FORBIDDEN = "You cannot assign ROOT ADMIN role.";

    public static final String UNAUTHORIZED = "You are not authorized to do that!";
    public static final String NOT_FOUND = "We couldn't find what you were looking for!";
    public static final String GENERAL_ERROR = "Something went wrong!";

    private ErrorMessages() {
    }
}
