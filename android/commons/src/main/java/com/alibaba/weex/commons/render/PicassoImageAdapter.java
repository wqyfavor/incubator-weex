/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.alibaba.weex.commons.render;

import android.net.Uri;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.taobao.weex.render.image.FrameImage;
import com.taobao.weex.render.image.FrameImageAdapter;
import com.taobao.weex.render.manager.RenderManager;
import com.taobao.weex.render.sdk.RenderSDK;

/**
 * Created by furture on 2018/8/10.
 */

public class PicassoImageAdapter extends FrameImageAdapter {

    @Override
    public FrameImage requestFrameImage(String url, final int width, final int height, int callbackId) {
        if(url.startsWith("//")){
            url = "https:" + url;
        }
        final String imageUrl = url;
        PicassoImageTarget loadImageTarget = new PicassoImageTarget(imageUrl, width, height, callbackId);
        final PicassoImageTarget requestTarget  = loadImageTarget;
        RenderManager.getInstance().getUiHandler().post(new Runnable() {
            @Override
            public void run() {
                RequestCreator requestCreator = Picasso.with(RenderSDK.getInstance().getApplication()).load(Uri.parse(imageUrl));
                if(width > 0 && height > 0){
                    requestCreator.resize(width, height);
                }
                requestCreator.into(requestTarget);
            }
        });
        return loadImageTarget;
    }
}
