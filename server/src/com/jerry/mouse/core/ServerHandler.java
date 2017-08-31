package com.jerry.mouse.core;

import com.jerry.mouse.api.Request;
import com.jerry.mouse.api.Response;

public interface ServerHandler {
    void handlePath(String path, Request request, Response response);
}
