package com.pos.controller;

import org.springframework.stereotype.Controller;


/**
 * Handles requests for the application home page.
 */
@Controller
public class PosCommonController{
	/*
	Logger log = LoggerFactory.getLogger(this.getClass());

	// 어드민에서 호출
	@RequestMapping(value = "/pos/poseBackupData")
	public ModelAndView poseBackupData() throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		mv.addObject("result", makeBackupData(true));
        return mv;
	}
	@RequestMapping(value = "/pos/poseBackupDataSendMail")
	public ModelAndView poseBackupDataSendMail() throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		mv.addObject("result", sendMailBackupData(true));
		return mv;
	}
	
	// 백업 파일 만드는 메소드
	public boolean makeBackupData(boolean retry){
		ApplicationContext applicationContext = ApplicationContextAware.getApplicationContext();
		PosCommonService posCommonService = (PosCommonService)applicationContext.getBean("posCommonService");
		
		Date date = new Date();
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 E요일 HH:mm:ss");
		
		HashMap<String, Object> tableMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = null;
		
		try {
			// 백업파일 저장 디렉토리 경로를 프로퍼티에서 읽어오기
			String directoryFile = PropertyManager.getPropertyValue("posSetting", "FILE_DIRECTORY", "saveFile");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
			// 디렉토리 + 파일명 만들기
			String saveFile = directoryFile + "/posBackup_" + sdf2.format(date) + ".txt";
						
			File file = new File(saveFile);
			if (file.isFile()) {
				if(!retry) {
					log.debug("txt파일이 존재합니다.[" + file.getName().toString() + "]");
					log.debug("이미 백업파일을 만들었기 때문에 다시 만들지 않습니다.");
					return true;
				}
			}
			// 프로퍼티에서 백업할 테이블 명 가져오기
			String[] backupListArr = PropertyManager.getPropertyValue("posSetting", "BACKUP_TABLE", "dual").split("\\|");
			if(log.isDebugEnabled()){
				log.debug(Arrays.toString(backupListArr));
			}
			
			// 테이블별로 백업 데이터 만들기
			for(String backupList : backupListArr) {
				tableMap.clear();
				tableMap.put("BACKUP_TABLE", backupList);
				list = posCommonService.selectBackupData(tableMap);
				
				if(log.isDebugEnabled()){
					log.debug(list.toString());
				}
				
				sb.append("\n\n--------------------------------------------------------\n");
				sb.append("--  파일이 생성됨 - " + sdf.format(date) + "\n");
				sb.append("--------------------------------------------------------\n");
				sb.append("--------------------------------------------------------\n");
				sb.append("--  DDL for Table " + tableMap.get("BACKUP_TABLE") + "\n");
				sb.append("--------------------------------------------------------\n");
				sb.append("TRUNCATE TABLE " + tableMap.get("BACKUP_TABLE") + ";\n");
				for(int i = 0; i < list.size(); i++) {
					sb.append("INSERT INTO " + tableMap.get("BACKUP_TABLE") + "(");
					String tableFieldList = "";
					String tableFieldValueList = "";
					for(String tableField : list.get(i).keySet()) {
						log.debug("key : [" + tableField + "], value : [" + list.get(i).get(tableField) + "]");
						if(!"".equals(tableFieldList)) {
							tableFieldList = tableFieldList + ", " + tableField;
							tableFieldValueList = tableFieldValueList + ", '" + list.get(i).get(tableField).toString().replace("'", "''") + "'";
						} else {
							tableFieldList = tableField;
							tableFieldValueList = "'" + list.get(i).get(tableField) + "'";
						}
					}
					sb.append(tableFieldList + ") VALUES (" + tableFieldValueList + ");\n");
				}
				
			}
			sb.append("\nCOMMIT;\n");
			
			// 파일 만들기 실행
			FileManager.createTxtFile(saveFile, sb, true);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	// 메일 보내기
	public boolean sendMailBackupData(boolean retry) {
		ApplicationContext applicationContext = ApplicationContextAware.getApplicationContext();
		JavaMailSenderImpl mailSender = (JavaMailSenderImpl) applicationContext.getBean("wonsukMailSender");
		
		try {
			final Date date = new Date();
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			// 백업파일
			final String txtFilename = PropertyManager.getPropertyValue("posSetting", "FILE_DIRECTORY", "saveFile") + "/posBackup_" + sdf.format(date) + ".txt";
			final String zipFilename = PropertyManager.getPropertyValue("posSetting", "FILE_DIRECTORY", "saveFile") + "/posBackup_" + sdf.format(date) + ".zip";
			
			// 파일이 있는지 확인
			File file = new File(zipFilename);
			if (file.isFile()) {
				if(!retry) {
					log.debug("zip파일이 존재합니다.[" + file.getName().toString() + "]");
					log.debug("이미 메일을 보냈기 때문에 다시 보내지 않습니다.");
					return true;
				}
			}
			
			// zip파일 생성
			log.debug("[" + file.getName().toString() + "] zip 파일을 생성합니다.");
			byte[] buf = new byte[4096];
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFilename));
			FileInputStream in = new FileInputStream(txtFilename);
			Path p = Paths.get(txtFilename);
			String fileName = p.getFileName().toString();
			ZipEntry ze = new ZipEntry(fileName);
			out.putNextEntry(ze);
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.closeEntry();
			in.close();
			out.close();
			
			// 메일 보내는 함수
			final MimeMessagePreparator preparator = new MimeMessagePreparator() {
				@Override
				public void prepare(MimeMessage mimeMessage) throws Exception {
					final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
					
					String subject = "포스기 백업 파일_" + sdf.format(date);
					String body = "포스기 백업 데이터 입니다.";
					
					// 보내는사람
					helper.setFrom("wschoi@neobns.com");
					// 받는사람
					helper.setTo("hyunsbistro@naver.com");
					// 메일제목
					helper.setSubject(subject);
					// 메일내용
					helper.setText(body);
					// 첨부파일
					FileSystemResource file = new FileSystemResource(new File(zipFilename));
					helper.addAttachment(zipFilename, file);
				}
			};
			mailSender.send(preparator);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		PosCommonController test = new PosCommonController();
		test.sendMailBackupData(true);
	}
	*/
}