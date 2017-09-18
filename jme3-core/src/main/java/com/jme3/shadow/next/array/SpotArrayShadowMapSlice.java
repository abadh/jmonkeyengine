/*
 * Copyright (c) 2009-2016 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.jme3.shadow.next.array;

import com.jme3.light.SpotLight;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.GeometryList;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Spatial;
import com.jme3.shadow.ShadowUtil;
import com.jme3.texture.TextureArray;

/**
 * @author Kirill Vainer
 */
public class SpotArrayShadowMapSlice extends BaseArrayShadowMapSlice<SpotLight> {

    public SpotArrayShadowMapSlice(TextureArray array, int layer, int textureSize) {
        super(array, layer, textureSize, true);
    }

    public void updateShadowCamera(ViewPort viewPort, SpotLight light, GeometryList shadowCasters) {
        shadowCamera.setLocation(light.getPosition());
        shadowCamera.lookAtDirection(light.getDirection(), Vector3f.UNIT_Y);
        shadowCamera.setFrustumPerspective(light.getSpotOuterAngle() * FastMath.RAD_TO_DEG * 2.0f, 1, 1, light.getSpotRange());
        for (Spatial scene : viewPort.getScenes()) {
            ShadowUtil.getGeometriesInCamFrustum(scene, shadowCamera, RenderQueue.ShadowMode.Cast, shadowCasters);
        }
    }
}
