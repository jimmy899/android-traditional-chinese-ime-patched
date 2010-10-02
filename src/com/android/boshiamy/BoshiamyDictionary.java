/*
 * Copyright 2010 Google Inc.
 *
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
 */

package com.android.boshiamy;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;

import com.android.boshiamy.R;

/**
 * Extends WordDictionary to provide zhuyin word-suggestions.
 */
public class BoshiamyDictionary extends WordDictionary {

  private static final int APPROX_DICTIONARY_SIZE = 256 * 1024;

  public BoshiamyDictionary(Context context) {
    super(context, R.raw.dict_boshiamy, APPROX_DICTIONARY_SIZE);
  }

@SuppressWarnings("unchecked")
@Override
  public String getWords(CharSequence input) {
    HashMap<String, String> dictionary = null;
    input = (CharSequence) input.toString().toLowerCase();
    Object dic = dictionary();
    if ( dic instanceof HashMap ) {
    	dictionary = (HashMap<String, String>) dic;
    } else {
    	Log.e("BoshiamyDictionary", "incorrect dictionary file");
    	return "";
    }

	Log.i("boshiamy", "input: " + input + ", " + dic.getClass().toString() );

	if ( input.length() > 0 ) {
	    String r = dictionary.get(input.toString());
	    if ( r != null ) {
	    	Log.i("boshiamy", "output: " + r + ", length" + r.length() );;
		    return r;
	    } else {
	    	if ( input.length() > 1 ) {
		    	r = dictionary.get(input.subSequence(0, input.length() - 1).toString());
		    	Log.i("boshiamy", "reduced input: " + input.subSequence(0, input.length() - 1).toString() );
		    	if (r != null && input.charAt(input.length() - 1) == 'v') {
			    	Log.i("boshiamy", "output: " + r.substring(1) + ", length: " + r.length() );
		    		return r.length() > 1 ? r.substring(1) : "";
		    	} else {
		    		Log.i("Boshiamy", "lastchar: " + input.charAt(input.length() - 1));
			    	Log.i("boshiamy", "output: empty " );
		    	}
	    	} else {
		    	Log.i("boshiamy", "input length: " + input.length() + ", <= 1" );
	    	}
	    }
    } else {
    	Log.i("boshiamy", "input length: " + input.length() + ", <= 0" );
	}
    return "";
  }
}
