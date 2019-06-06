package edu.handong.csee.merge;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipFiles {

	public static String decompress(String Input_path, String Output_path) throws Throwable {
		File zipFile = new File(Input_path);
		FileInputStream fis = null;
		ZipInputStream zis = null;
		ZipEntry zipentry = null;

		try {
			// ���� ��Ʈ��
			fis = new FileInputStream(zipFile);
			// Zip ���� ��Ʈ��
			zis = new ZipInputStream(fis);

			// entry�� ���������� �̱�
			while ((zipentry = zis.getNextEntry()) != null) {
				String filename = zipentry.getName();
				File file = new File(Output_path, filename);
				
				// entry�� ������ ���� ����
				if (zipentry.isDirectory()) {
					file.mkdirs();
				} else {
					// �����̸� ���� �����
					createFile(file, zis);
				}
			}

		} catch (Throwable e) {
			throw e;
		} finally {
			if (zis != null)
				zis.close();
			if (fis != null)
				fis.close();
		}
		return Output_path;

	}

	/**
	 * ���� ����� �޼ҵ�
	 * 
	 * @param file ����
	 * @param zis  Zip��Ʈ��
	 */
	private static void createFile(File file, ZipInputStream zis) throws Throwable {
		// ���丮 Ȯ��
		File parentDir = new File(file.getParent());
		// ���丮�� ������ ��������
		if (!parentDir.exists()) {
			parentDir.mkdirs();
		}
		// ���� ��Ʈ�� ����
		try (FileOutputStream fos = new FileOutputStream(file)) {
			byte[] buffer = new byte[256];
			int size = 0;
			// Zip��Ʈ�����κ��� byte�̾Ƴ���
			while ((size = zis.read(buffer)) > 0) {
				// byte�� ���� �����
				fos.write(buffer, 0, size);
			}
		} catch (Throwable e) {
			throw e;
		}
	}
}
