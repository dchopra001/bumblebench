/*******************************************************************************
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*******************************************************************************/

package net.adoptopenjdk.bumblebench.string;

import java.util.Locale;
import java.util.Random;

import net.adoptopenjdk.bumblebench.core.MiniBench;

import java.io.File;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.lang.StringBuilder;

public final class MaxMin extends MiniBench {

	private static final int MAX_NUM_LOOPS = option("maxNumLoops", 1000);
        private static final int split = option("split", 50);

        private static int [] input = {1, 2,  3, 4,  5,  6,  7,  8,  9,  10}; // first array of elements used for comparison
        private static int [] input2;
        private static int [] split_50 = {0, 10, 2, 11, 4,  12, 6,  13, 8,  14}; // corresponding elements in this array will be greater than the array named "input" 50% of the time
        private static int [] split_40 = {0, 10, 2, 11, 4,   1, 6,  13, 8,  14};
        private static int [] split_30 = {0, 10, 2, 11, 4,   1, 6,   0, 8,  14};
        private static int [] split_20 = {0,  1, 2, 11, 4,   1, 6,   0, 8,  14};
        private static int [] split_10 = {0,  1, 2,  1, 4,   1, 6,   0, 8,  14};
        private static int [] split_0  = {0, 1, 2,  3, 4,  5,  6,  7,  8,  9  };

       static {

              if (split == 50) {
                   input2 = split_50; 
              }
              else if (split == 40) {
                   input2 = split_40;
              }
              else if (split == 30) {
                   input2 = split_30;
              }
              else if (split == 20) {
                   input2 = split_20;
              }
              else if (split == 10) {
                   input2 = split_10;
              }
              else if (split == 0) {
                   input2 = split_0;
              }


      }

	protected int maxIterationsPerLoop() {
		return MAX_NUM_LOOPS;
	}

      protected int runMaxMinWorkload(int i1, int i2) {
      		int rc = 0;
                int res = Math.max(i1, i2);
                if (res == i1)
                   rc = 5;
                else
                   rc = 10;
                return rc;
      }

	protected long doBatch(long numLoops, int numIterationsPerLoop) throws InterruptedException {

                for (long loop = 0; loop < numLoops; loop++) {
			startTimer();
                     for (int j = 0; j < MAX_NUM_LOOPS; j++) {
			for (int i = 0; i < input.length; i++)
                		runMaxMinWorkload(input[i], input2[i]);
                     }
			pauseTimer();
		}

		return numLoops * numIterationsPerLoop;
	}
}
