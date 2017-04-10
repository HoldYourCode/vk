package com.holdyourcolour.myvk.api;

import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKParser;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.methods.VKApiMessages;
import com.vk.sdk.api.model.VKApiGetDialogResponse;
import com.vk.sdk.api.model.VKApiGetMessagesResponse;
import com.vk.sdk.api.model.VKApiMessage;

import org.json.JSONObject;

/**
 * Created by HoldYourColour on 02.03.2017.
 */

public class VKApiMessagesExtension extends VKApiMessages {

    /**
     * https://vk.com/dev/messages.getHistory
     *
     * @param params use parameters from description with VKApiConst class
     * @return Request for load
     */
    public VKRequest getHistory(VKParameters params) {
        return prepareRequest("getHistory", params, new VKParser() {
            @Override
            public Object createModel(JSONObject object) {
                return new VKApiGetMessagesResponse(object);
            }
        });
    }
}
