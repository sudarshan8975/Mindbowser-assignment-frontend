package com.example.list.assigmnent.service;

public class ApiManager {
    private static ApiManager instance;
    private static Services apiService;

    private ApiManager() {
        apiService = ApiServiceFactory.makeApiServiceService();
    }

    public static ApiManager getInstance() {
        if (instance == null) {
            instance = new ApiManager();
        }
        return instance;
    }

    public Services getApiService() {
        if (apiService == null) {
            apiService = ApiServiceFactory.makeApiServiceService();
        }
        return apiService;
    }

}
