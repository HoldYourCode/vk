package com.holdyourcolour.myvk.api;

import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.methods.VKApiPhotos;
import com.vk.sdk.api.model.VKPhotoArray;

/**
 * Created by HoldYourColour on 08.03.2017.
 */

public class VKApiPhotosExtension extends VKApiPhotos {

    public VKRequest getPhotos(VKParameters params) {
        return prepareRequest("get", params, VKPhotoArray.class);

    }
}
