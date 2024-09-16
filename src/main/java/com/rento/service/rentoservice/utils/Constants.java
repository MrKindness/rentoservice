package com.rento.service.rentoservice.utils;

public class Constants {
    public static final class DATA_BASE {
        public static final String SCHEMA = "rento_schema";
        public static final String ROLE_TABLE = "role";
        public static final String USER_TABLE = "user";
        public static final String USER_ROLE_TABLE = "user_role";
        public static final String TRANSPORT_TABLE = "transport";
    }

    public static class API {

        public static final String ROOT = "/api";

        public static final class AUTH {
            public static final String ROOT = API.ROOT + "/auth";
            public static final String REGISTER = "/register";
        }

        public static final class USER {
            public static final String ROOT = API.ROOT + "/user";
            public static final String USER_UPDATE = "/update";
            public static final String USER_BY_USERNAME = "/{username}";
            public static final String ADMIN = "/admin";
        }

        public static final class TRANSPORT {
            public static final String ROOT = API.ROOT + "/transport";
            public static final String TRANSPORT_BY_ID = "/{transportId}";
            public static final String OWNER_TRANSPORTS = "/owner";
            public static final String AVAILABLE_TRANSPORTS = "/available";
            public static final String RENTED_TRANSPORTS = "/rented";
            public static final String RENT_TRANSPORT = "/rent" + TRANSPORT_BY_ID;
        }
    }
}