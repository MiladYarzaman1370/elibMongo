package com.Hr.Project;

public  class RegularExpertion {

    public static boolean checkEmail(String email){

         final String EMAIL_VERIFICATION = "^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$";

        if(email.matches(EMAIL_VERIFICATION)){
            return true;
        }else{
            return false;
        }
    }
    public static boolean checkPassword(String password){
        final String PASSWORD_VERIFICATION =  "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
        if(password.matches(PASSWORD_VERIFICATION)){
            return true;
        }else{
            return false;
        }
    }
}
