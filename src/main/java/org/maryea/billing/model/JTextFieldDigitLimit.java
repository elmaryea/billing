package org.maryea.billing.model;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

public class JTextFieldDigitLimit extends PlainDocument{

	private static final long serialVersionUID = 742207652753621575L;
	private int limit;
	private DocumentFilter filter;
	
	public JTextFieldDigitLimit(int limit){
		super();
		this.limit = limit;
	}
	
	public void insertString(int offset, String  str, AttributeSet attr) throws BadLocationException {
		if (str == null) return;

    if ((getLength() + str.length()) <= limit) {
      super.insertString(offset, str.replaceAll("\\D++", ""), attr);
    }
	}
	
}
