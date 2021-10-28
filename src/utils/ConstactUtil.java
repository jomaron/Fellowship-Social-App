package utils;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

public class ConstactUtil {
	/**
	 * ��ȡ��������
	 * 
	 * @return
	 */
	public static Map<String,String> getAllCallRecords(Context context) {
		Map<String,String> temp = new HashMap<String, String>();
		Cursor c = context.getContentResolver().query(
				ContactsContract.Contacts.CONTENT_URI,
				null,
				null,
				null,
				ContactsContract.Contacts.DISPLAY_NAME
						+ " COLLATE LOCALIZED ASC");
		if (c.moveToFirst()) {
			do {
				// �����ϵ�˵�ID��
				String contactId = c.getString(c
						.getColumnIndex(ContactsContract.Contacts._ID));
				// �����ϵ������
				String name = c
						.getString(c
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				// �鿴����ϵ���ж��ٸ��绰���롣���û���ⷵ��ֵΪ0
				int phoneCount = c
						.getInt(c
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
				String number=null;
				if (phoneCount > 0) {
					// �����ϵ�˵ĵ绰����
					Cursor phones = context.getContentResolver().query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ " = " + contactId, null, null);
					if (phones.moveToFirst()) {
						number = phones
								.getString(phones
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						}
					phones.close();
				}
				temp.put(name, number);
			} while (c.moveToNext());
		}
		c.close();
		return temp;
	}
}