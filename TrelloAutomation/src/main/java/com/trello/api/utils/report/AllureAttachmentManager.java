package com.trello.api.utils.report;

import io.qameta.allure.Attachment;

public class AllureAttachmentManager {

    @Attachment(value = "Execution Console Logs Logs", type = "text/plain")
    public static String attachTextLog(String message) {
        return message;
    }

    @Attachment(value = "API Error Message Details", type = "application/json")
    public static String attachJsonPayload(String json) {
        return json;
    }
}