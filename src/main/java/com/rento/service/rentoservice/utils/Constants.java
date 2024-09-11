package com.rento.service.rentoservice.utils;

public class Constants {
    public static final class DATA_BASE {
        public static final String SCHEMA = "rento_schema";
        public static final String USER_TABLE = "user";
        public static final String ROLE_TABLE = "role";
        public static final String USER_ROLE_TABLE = "user_role";
    }

    public static class API {

        public static final String ROOT = "/api";

        public static final class AUTH {
            public static final String ROOT = API.ROOT + "/auth";
            public static final String REGISTER = "/register";
        }

        public static final class USER {
            public static final String ROOT = API.ROOT + "/user";
        }
    }
}