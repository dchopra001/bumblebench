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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;

/**
 * 
 * 
 * This benchmark can generate strings of fixed/variable lengths and invokes
 * toUpper/toLower in such a manner that the ternary IL is generated. We can 
 * then measure the performance of instructions designed to optimize the 
 * execution of such IL.
 * 
 * The reported score is in terms of number of string conversions per sec.
 * String length is by default 877, and can be randomnized using options.
 * 
 * Note: adding to much randomnization to bumblebench workloads seems to make
 * the benchmark framework a little to aggressive in terms of estimating the
 * target threashold.
 * 
 * */

public final class StringTernary extends MiniBench {

	private static final int MAX_NUM_STRINGS = option("maxNumStrings", 1000);

	private static final int STRING_LENGTH = option("stringLength", 0);
	private static final boolean IS_PSUEDO_RANDOM = option("random", false);
        private static final int r = option("distribution", 0);

	private static Random rand;

        private static ArrayList<Integer> al1 = new ArrayList<Integer>();
        private static HashSet<Integer> s1 = new HashSet<Integer>();
        private static Object obj1 = new Object();
        private static HashMap <Integer, Integer> h1 = new HashMap<Integer, Integer>();
        private static StringBuilder sb1 = new StringBuilder();


        private static Object[] objectsArray = {al1, s1, obj1, rand, h1, sb1};

	private static String[] strings;

	static {
		strings = new String[MAX_NUM_STRINGS];
		rand = new Random();
		int length;
		StringBuilder sb = new StringBuilder(1000);

		if (!IS_PSUEDO_RANDOM) {
			rand.setSeed(12345L);
		}

		if (STRING_LENGTH <= 0  || STRING_LENGTH > 1000) {
			for (int i = 0; i < MAX_NUM_STRINGS; i++) {
				length = rand.nextInt(1000);
				sb.setLength(0);
				for (int j = 0; j < length; ++j){
					sb.append(StringTestData.POSSIBLE_CHARS[rand.nextInt(StringTestData.POSSIBLE_CHARS.length)]);
				}
				strings[i] = sb.toString();
			}
		} else {
			length = STRING_LENGTH;
			for (int i = 0; i < MAX_NUM_STRINGS; ++i) {
				sb.setLength(0);
				for (int j = 0; j < length; ++j){
					sb.append(StringTestData.POSSIBLE_CHARS[rand.nextInt(StringTestData.POSSIBLE_CHARS.length)]);
				}
				strings[i] = sb.toString();
			}
		}
	}

	protected int maxIterationsPerLoop() {
		return MAX_NUM_STRINGS;
	}

       private int runTernaryWorkload(Object foo) {
                int rc = 0;
       		if (foo instanceof String) {
         		//((String)foo).toLowerCase();
         		rc = 1;
                        return rc;
      		}
    		rc = 10;
                return rc;   
       }

	protected long doBatch(long numLoops, int numIterationsPerLoop) throws InterruptedException {
                for (long loop = 0; loop < numLoops; loop++) {
			startTimer();
                     for (int j = 0; j < MAX_NUM_STRINGS; j++) {   
			for (int i = 0; i < objectsArray.length; i++)
                		runTernaryWorkload(objectsArray[i]);
                     }
			pauseTimer();
	        }

		return numLoops * numIterationsPerLoop;
	}
}
