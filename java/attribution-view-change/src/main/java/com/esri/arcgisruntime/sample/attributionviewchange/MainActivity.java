/* Copyright 2018 Esri
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.esri.arcgisruntime.sample.attributionviewchange;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.MapView;

public class MainActivity extends AppCompatActivity {

  private MapView mMapView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // inflate MapView from layout
    mMapView = findViewById(R.id.mapView);
    // create a map with a web mercator basemap
    ArcGISMap map = new ArcGISMap(Basemap.Type.TOPOGRAPHIC, 47.495052, -121.786863, 12);
    // set the map to be displayed in this view
    mMapView.setMap(map);

    // create a FAB to respond to attribution bar
    FloatingActionButton mFab = findViewById(R.id.floatingActionButton);
    mFab.setOnClickListener(v -> Snackbar.make(v, "Button responds to attribution bar", Snackbar.LENGTH_LONG)
        .setAction("Action", null).show());

    // set attribution bar listener
    mMapView.addAttributionViewLayoutChangeListener(
        (v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
          int heightDelta = oldBottom - bottom;
          mFab.animate().translationYBy(heightDelta);
          Toast.makeText(MainActivity.this, "new bounds [" + left + ',' + top + ',' + right + ',' + bottom + ']' +
              " old bounds [" + oldLeft + ',' + oldTop + ',' + oldRight + ',' + oldBottom + ']', Toast.LENGTH_SHORT)
              .show();
        });
  }

  @Override
  protected void onPause() {
    super.onPause();
    mMapView.pause();
  }

  @Override
  protected void onResume() {
    super.onResume();
    mMapView.resume();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mMapView.dispose();
  }
}