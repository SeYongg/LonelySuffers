package common;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileManager {

	public String upload(String savePath, MultipartFile file) {
		String filename = file.getOriginalFilename();
		//filename => test.txt -> test       .txt
		String onlyFilename = filename.substring(0, filename.lastIndexOf("."));//test
		String extention = filename.substring(filename.lastIndexOf("."));//.txt
		//실제 업로드할 파일명
		String filepath = null;
		//파일명 중복 시 뒤에 부일 숫자
		int count = 0;
		while(true) {
			if(count == 0) {
				//첫번째 검증인 경우 숫자 붙이지 않음
				filepath = onlyFilename+extention; //text.txt
			}else {
				filepath = onlyFilename+"_"+count+extention; //text_1.txt
			}
			File checkFile = new File(savePath+filepath);
			if(!checkFile.exists()) {
				break;
			}
			count++;
		}
		//파일명 중복체크 끝 -> 업로드 파일명 확정 -> 파일업로드 진행
		//2-2. 중복처리가 끝난 파일 업로드
		try {
			//파일업로드를 위한 주스트림 생성
			FileOutputStream fos = new FileOutputStream(savePath+filepath);
			//성능향상을 위한 보조스트림 생성
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			//파일업로드
			byte[] bytes = file.getBytes();
			bos.write(bytes);
			bos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filepath;
	}


// 이미지파일만 업로드
	public String uploadImg(String savePath, MultipartFile file) {
		String filename = file.getOriginalFilename();
		String onlyFilename = filename.substring(0, filename.lastIndexOf("."));//test
		String extention = filename.substring(filename.lastIndexOf("."));//.txt
		String filepath = null;
		if (extention.equalsIgnoreCase(".jpg") || extention.equalsIgnoreCase(".jpeg") || extention.equalsIgnoreCase(".png") || extention.equalsIgnoreCase(".gif") || extention.equalsIgnoreCase(".webp")) {
			//실제 업로드할 파일명
			//파일명 중복 시 뒤에 부일 숫자
			int count = 0;
			while(true) {
				if(count == 0) {
					//첫번째 검증인 경우 숫자 붙이지 않음
					filepath = onlyFilename+extention; //text.txt
				}else {
					filepath = onlyFilename+"_"+count+extention; //text_1.txt
				}
				File checkFile = new File(savePath+filepath);
				if(!checkFile.exists()) {
					break;
				}
				count++;
			}
			//파일명 중복체크 끝 -> 업로드 파일명 확정 -> 파일업로드 진행
			//2-2. 중복처리가 끝난 파일 업로드
			try {
				FileOutputStream fos = new FileOutputStream(savePath+filepath);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				byte[] bytes = file.getBytes();
				bos.write(bytes);
				bos.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return filepath;
	}

	public boolean deleteFile(String savePath, String filepath) {
		File delFile = new File(savePath+filepath);
		return delFile.delete();
	}
}
