package edu.handong.csee.merge;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipFiles {

	public static void decompress(String zipFileName, String directory) throws Throwable {
		File zipFile = new File(zipFileName);
		FileInputStream fis = null;
		ZipInputStream zis = null;
		ZipEntry zipentry = null;
		try {
		//���� ��Ʈ��
		fis = new FileInputStream(zipFile);
		//Zip ���� ��Ʈ��
		zis = new ZipInputStream(fis);
		//entry�� ���������� �̱�
		while ((zipentry = zis.getNextEntry()) != null) {
		String filename = zipentry.getName();
		File file = new File(directory, filename);
		//entiry�� ������ ���� ����
		if (zipentry.isDirectory()) {
		file.mkdirs();
		} else {
		//�����̸� ���� �����
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
		}
		/**
		* ���� ����� �޼ҵ�
		* @param file ����
		* @param zis Zip��Ʈ��
		*/
		private static void createFile(File file, ZipInputStream zis) throws Throwable {
		//���丮 Ȯ��
		File parentDir = new File(file.getParent());
		//���丮�� ������ ��������
		if (!parentDir.exists()) {
		parentDir.mkdirs();
		}
		//���� ��Ʈ�� ����
		try (FileOutputStream fos = new FileOutputStream(file)) {
		byte[] buffer = new byte[256];
		int size = 0;
		//Zip��Ʈ�����κ��� byte�̾Ƴ���
		while ((size = zis.read(buffer)) > 0) {
		//byte�� ���� �����
		fos.write(buffer, 0, size);
		}
		} catch (Throwable e) {
		throw e;
		}
		}
		public static void main(String[] args){
		try{
		Zip.decompress("D:\\test.zip", "D:\\test1");
		}catch(Throwable e){
		e.printStackTrace();
		}
		}
		}


}
