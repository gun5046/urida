/*
 * Copyright 2020 Google LLC. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.mlkit.vision.demo.kotlin.objectdetector

import android.content.Context
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import com.edu.mf.R
import com.edu.mf.view.picture.GraphicOverlay
import com.edu.mf.view.picture.VisionProcessorBase
import com.google.android.gms.tasks.Task
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.DetectedObject
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.ObjectDetector
import com.google.mlkit.vision.objects.ObjectDetectorOptionsBase
import java.io.IOException

/** A processor to run object detector.  */
class ObjectDetectorProcessor(val context: Context, options: ObjectDetectorOptionsBase, val translator: Translator) :
  VisionProcessorBase<List<DetectedObject>>(context) {

  private val detector: ObjectDetector = ObjectDetection.getClient(options)

  override fun stop() {
    super.stop()
    try {
      detector.close()
    } catch (e: IOException) {
      Log.e(
        TAG,
        "Exception thrown while trying to close object detector!",
        e
      )
    }
  }

  override fun detectInImage(image: InputImage): Task<List<DetectedObject>> {
    return detector.process(image)
  }

  override fun onSuccess(results: List<DetectedObject>, graphicOverlay: GraphicOverlay) {
    for (result in results) {
//      for(label in result.labels){
//        translator.translate(label.text).addOnSuccessListener {
//          Log.d(TAG, "onSuccess: ${label.text}, ${it}")
//          graphicOverlay.add(ObjectGraphic(graphicOverlay, result, it))
//        }
//      }
      Log.d(TAG, "onSuccess: ${result.labels.size}")
      if(result.labels.isNotEmpty()){
        translator.translate(result.labels[0].text).addOnSuccessListener {
          Log.d(TAG, "onSuccess: ${result.labels[0].text}, ${it}")
          var str = it
          if(it.equals("쥐")){
            str = "마우스"
          } else if(it.equals("수하물 및 가방")){
            str = "가방"
          }
          graphicOverlay.add(ObjectGraphic(graphicOverlay, result, str, ResourcesCompat.getFont(context, R.font.kakao_bold)!!))
          graphicOverlay.postInvalidate()
        }
      }
    }
  }

  override fun onFailure(e: Exception) {
    Log.e(TAG, "Object detection failed!", e)
  }

  companion object {
    private const val TAG = "ObjectDetectorProcessor"
  }
}
