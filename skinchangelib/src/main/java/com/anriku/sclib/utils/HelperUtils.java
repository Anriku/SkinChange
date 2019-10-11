package com.anriku.sclib.utils;

import com.anriku.sclib.helpers.SCHelper;
import com.anriku.sclib.widget.SCTextView;

/**
 * Created by anriku on 2019-10-11.
 */

public class HelperUtils {

    public static void main(String[] args) {
        Class c = SCTextView.class;
    }

    public static int[] secureRecordAndReplaceResIds(SCHelper helper, int[] resIds) {
        int[] newResIds = helper.recordAndReplaceResIds(resIds);
        if (newResIds == null || newResIds.length != resIds.length) {
            throw new HelperResultWrongException();
        }
        return newResIds;
    }

    /**
     * {@link com.anriku.sclib.helpers.SCHelper}的recordAndReplaceResIds方法返回的新的资源数组为null
     * 或者长度不等于传入的原始资源数组的长度的时候抛出
     */
    public static class HelperResultWrongException extends RuntimeException {

        public HelperResultWrongException() {
            super("Please check the result of recordAndReplaceResIds function in your subclass of com.anriku.sclib.helpers.SCHelper");
        }
    }

}
