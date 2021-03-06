/**
 * Copyright 2012 The PlayN Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package playn.ios;

import cli.MonoTouch.CoreGraphics.CGImage;

import playn.core.Canvas;
import playn.core.CanvasImage;

/**
 * Provides {@link Canvas} rendering into an image.
 */
public class IOSCanvasImage extends IOSAbstractImage implements CanvasImage {

  private final IOSCanvas canvas;

  public IOSCanvasImage(IOSGLContext ctx, float width, float height) {
    super(ctx, ctx.scale);
    canvas = new IOSCanvas(ctx, width, height);
  }

  @Override
  public Canvas canvas() {
    return canvas;
  }

  @Override
  public float width() {
    return canvas.width();
  }

  @Override
  public float height() {
    return canvas.height();
  }

  @Override
  public int ensureTexture(boolean repeatX, boolean repeatY) {
    // if we have a canvas, and it's dirty, force the recreation of our texture which will obtain
    // the latest canvas data
    if (canvas.dirty()) {
      canvas.clearDirty();
      clearTexture();
    }
    return super.ensureTexture(repeatX, repeatY);
  }

  @Override
  public void setRgb(int startX, int startY, int width, int height, int[] rgbArray, int offset,
                     int scanSize) {
    throw new UnsupportedOperationException();
  }

  @Override
  protected CGImage cgImage() {
    return canvas.cgImage();
  }

  @Override
  protected void updateTexture(int tex) {
    ((IOSGLContext) ctx).updateTexture(tex, canvas.texWidth(), canvas.texHeight(), canvas.data());
  }
}
