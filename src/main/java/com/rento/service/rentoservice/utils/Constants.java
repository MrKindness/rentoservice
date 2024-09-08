package com.rento.service.rentoservice.utils;

public class Constants {
    public static final class DataBase {
        public static final String SCHEMA = "rento_schema";
        public static final String USER_TABLE = "user";
        public static final String ROLE_TABLE = "role";
    }

    public static class API {

        public static final String auth = "/auth";

        public static final String root = "/api";

        public static class User {
            public static final String root = API.root + "/user";
            public static final String userDetails = "/userDetails";
            public static final String colleagues = "/colleagues";
            public static final String self = "/self";
        }

        public static class Team {
            public static final String root = API.root + "/team";
            public static final String teams = "/teams";
            public static final String candidates = "/candidates";
        }
    }
}