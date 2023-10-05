package com.example.utils;

import com.example.constants.Validate;
import java.util.Objects;

public class CheckUtil {

	/**
	 * descriptionの文字数チェックを行う
	 *
	 * @param description
	 * @return boolean
	 */
	public static boolean checkDescriptionLength(String description) {
		// descriptionは000文字以下か
		if (Objects.nonNull(description) && description.length() > Validate.DESCRIPTION_TEXT_LENGTH) {
			return false;
		}
		return true;
	}
}
