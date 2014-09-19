﻿package money.question;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import common.base.SpringContextUtil;
import common.report.MyReport;
import common.report.ReportDaoUtil;
import common.report.ReportStrGenerate;
import common.report.ReportStrGenerate2;
import common.util.CommonUtil;
import common.util.NPOIReader;

import dwz.constants.BeanManagerKey;
import dwz.framework.core.exception.ValidateFieldsException;
import dwz.framework.utils.excel.XlsExport;
import dwz.present.BaseAction;

/**
 * 问题记录
 * 
 * @author lsq
 * 
 */
public class QuestionAction extends BaseAction implements ModelDriven<Object> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	QuestionManager pMgr = bf.getManager(BeanManagerKey.questionManager);
	private Question questionVo;

	public String beforeAdd() {
		return "detail";
	}

	/**
	 * 添加信息.
	 * 
	 * @return
	 */
	public String doAdd() {
		try {
			submit = getAppContext().getUser().getId();
			QuestionImpl questionImpl = new QuestionImpl(questionDesc,
					questionDate, consoleDate, answer, sort, orderId, status,
					submit);
			pMgr.createQuestion(questionImpl);
		} catch (ValidateFieldsException e) {
			log.error(e);
			return ajaxForwardError(e.getLocalizedMessage());
		}
		writeToPage(response, getText("msg.operation.success"));
		return null;
	}

	public String report() {
		return "report";
	}

	// 封装上传文件域的属性
	private File image;
	// 封装上传文件类型的属性
	private String imageContentType;
	// 封装上传文件名的属性
	private String imageFileName;
	// 接受依赖注入的属性
	private String savePath;

	public String reportXml() {
		response.setContentType("text/plain;charset=GBK");
		try {
			response.getWriter()
					.write("<graph showNamesalPrecision='0'><set name='USA' value='20'/><set name='USA' value='20'/></graph>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public String getImageContentType() {
		return imageContentType;
	}

	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	/**
	 * 删除信息.
	 * 
	 * @return
	 */
	public String doDelete() {
		String ids = request.getParameter("ids");
		pMgr.removeQuestion(ids);
		return ajaxForwardSuccess(getText("msg.operation.success"));
	}

	/**
	 * 得到详细信息.
	 * 
	 * @return
	 */
	public String beforeUpdate() {
		questionVo = pMgr.getQuestion(questionId);
		return "editdetail";
	}
	
	public String beforeImport() { 
		return "import";
	}

	/**
	 * 导入excel中的数据
	 * 
	 * @return
	 */
	public String importExcel() {
		FileInputStream fis;
		response.setContentType("text/html;charset=GBK");
		String result = "error";
		try {
			fis = new FileInputStream(getImage());
			NPOIReader reader = new NPOIReader(fis);
			List allSheetname = reader.getSheetNames();
			Iterator it = allSheetname.iterator();
			int count = 1;
			while (it.hasNext()) {
				System.out.println(count++ + ":" + it.next());
			}
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * 提供导入模版的下载
	 * 
	 * @return
	 */
	public String initImport() {
		response.setContentType("Application/excel");
		String fileNameString = CommonUtil.toUtf8String("导入问题列表模版.xls");
		response.addHeader("Content-Disposition", "attachment;filename="
				+ fileNameString);

		XlsExport e = new XlsExport();
		int rowIndex = 0;

		e.createRow(rowIndex++);
		for (ImportFiled filed : ImportFiled.values()) {
			e.setCell(filed.ordinal(), filed.toString());
		}

		e.exportXls(response);
		return null;
	}

	private static enum ImportFiled {
		SORT, QUESTIONDESC, QUESTIONDATE, CONSOLEDATE, ANSWER, STATUS;

		@Override
		public String toString() {
			switch (this.ordinal()) {
			case 0:
				return "问题类型";
			case 1:
				return "问题描述";
			case 2:
				return "发现日期";
			case 3:
				return "解决时间";
			case 4:
				return "解决方案";
			case 5:
				return "问题状态";
			default:
				return "";
			}
		}
	}

	private static enum ExportFiled {
		QUESTIONID, SORT, QUESTIONDESC, QUESTIONDATE, CONSOLEDATE, ANSWER, STATUS;

		@Override
		public String toString() {
			switch (this.ordinal()) {
			case 0:
				return "问题ID";
			case 1:
				return "问题类型";
			case 2:
				return "问题描述";
			case 3:
				return "发现日期";
			case 4:
				return "解决时间";
			case 5:
				return "解决方案";
			case 6:
				return "问题状态";
			default:
				return "";
			}
		}
	}

	public String export() {
		response.setContentType("Application/excel");
		String fileNameString = CommonUtil.toUtf8String("问题列表.xls");
		response.addHeader("Content-Disposition", "attachment;filename="
				+ fileNameString);

		int pageNum = getPageNum();
		int numPerPage = getNumPerPage();
		int startIndex = (pageNum - 1) * numPerPage;
		Map<QuestionSearchFields, Object> criterias = getCriterias();

		Collection<Question> questionList = pMgr.searchQuestion(criterias,
				questionQueryVO, realOrderField(), startIndex, numPerPage);

		XlsExport e = new XlsExport();
		int rowIndex = 0;

		e.createRow(rowIndex++);
		for (ExportFiled filed : ExportFiled.values()) {
			e.setCell(filed.ordinal(), filed.toString());
		}

		for (Question question : questionList) {
			e.createRow(rowIndex++);

			for (ExportFiled filed : ExportFiled.values()) {
				switch (filed) {
				case QUESTIONID:
					e.setCell(filed.ordinal(), question.getQuestionId());
					break;
				case QUESTIONDESC:
					e.setCell(filed.ordinal(), question.getQuestionDesc());
					break;
				case QUESTIONDATE:
					e.setCell(filed.ordinal(), question.getQuestionDate());
					break;
				case CONSOLEDATE:
					e.setCell(filed.ordinal(), question.getConsoleDate());
					break;
				case ANSWER:
					e.setCell(filed.ordinal(), question.getAnswer());
					break;
				case SORT:
					e.setCell(filed.ordinal(), question.getSortName());
					break;
				case STATUS:
					e.setCell(filed.ordinal(), question.getStatusName());
					break;
				default:
					break;
				}

			}
		}

		e.exportXls(response);
		return null;
	}

	/**
	 * 高级查询之前.
	 * 
	 * @return
	 */
	public String beforeQuery() {
		return "query";
	}

	/**
	 * 更新信息.
	 * 
	 * @return
	 */
	public String doUpdate() {
		try {
			QuestionImpl questionImpl = new QuestionImpl(questionId,
					questionDesc, questionDate, consoleDate, answer, sort,
					orderId, status, submit);
			pMgr.updateQuestion(questionImpl);
		} catch (ValidateFieldsException e) {
			e.printStackTrace();
		}
		writeToPage(response, getText("msg.operation.success"));
		return null;
	}

	private int page = 1;
	private int pageSize = 50;
	private long count;

	/**
	 * 查询信息.
	 * 
	 * @return
	 */
	public String query() {
		int pageNum = getPageNum();
		int numPerPage = getNumPerPage();
		int startIndex = (pageNum - 1) * numPerPage;
		Map<QuestionSearchFields, Object> criterias = getCriterias();

		Collection<Question> moneyList = pMgr.searchQuestion(criterias,
				questionQueryVO, realOrderField(), startIndex, numPerPage);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("numPerPage", numPerPage);
		request.setAttribute("totalCount",
				pMgr.searchQuestionNum(criterias, questionQueryVO));

		ActionContext.getContext().put("list", moneyList);
		ActionContext.getContext().put("questionVo", questionVo);
		ActionContext.getContext().put("questionQueryVO", questionQueryVO);
		return "list";
	}

	public String complexQuery() {
		return "goonquery";
	}

	public String reQuery() {
		return "list";
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	/**
	 * 根据问题状态进行统计报表
	 * 
	 * @return
	 */
	public String reportQuestionCountByStatus() {
		ReportDaoUtil util = (ReportDaoUtil) SpringContextUtil
				.getBean("reportUtil");
		String sql = new MyReport.Builder("question_v").groupBy("statusname")
				.count().colomns(new String[] { "statusname" }).build()
				.generateSql();
		String ans = util.getReportStr(sql, new ReportStrGenerate2() {
			@Override
			public String change(Map objs) {
				return "['" + objs.get("statusname") + "',"
						+ objs.get("count1") + " ]";
			}

		});
		writeToPage(response, ans);
		return null;
	}

	/**
	 * 根据问题类型统计报表.
	 * 
	 * @return
	 */
	public String reportQuestionCountByType() {
		ReportDaoUtil util = (ReportDaoUtil) SpringContextUtil
				.getBean("reportUtil");
		String sql = new MyReport.Builder("question_v").groupBy("typename")
				.count().colomns(new String[] { "typename" }).build()
				.generateSql();
		String ans = util.getReportStr(sql, new ReportStrGenerate2() {
			@Override
			public String change(Map objs) {
				return "['" + objs.get("typename") + "'," + objs.get("count1")
						+ " ]";
			}

		});
		writeToPage(response, ans);
		return null;
	}

	/**
	 * 根据问题提问人统计报表.
	 * 
	 * @return
	 */
	public String reportQuestionCountByPerson() {
		ReportDaoUtil util = (ReportDaoUtil) SpringContextUtil
				.getBean("reportUtil");
		String sql = new MyReport.Builder("question_v").groupBy("submit")
				.count().colomns(new String[] { "submit" }).build()
				.generateSql();
		String ans = util.getReportStr(sql, new ReportStrGenerate() {
			@Override
			public String change(Object[] objs) {
				return "['" + objs[1] + "'," + objs[0] + " ]";
			}

		});
		writeToPage(response, ans);
		return null;
	}

	private Map<QuestionSearchFields, Object> getCriterias() {
		Map<QuestionSearchFields, Object> criterias = new HashMap<QuestionSearchFields, Object>();
		QuestionVO vo = new QuestionVO();
		if (sort > 0) {
			criterias.put(QuestionSearchFields.SORT, sort);
			vo.setSort(sort);
		}
		if (CommonUtil.isNotEmpty(questionDesc)) {
			criterias.put(QuestionSearchFields.QUESTIONDESC, "%" + questionDesc
					+ "%");
			vo.setQuestionDesc(questionDesc);
		}
		if (status > 0) {
			criterias.put(QuestionSearchFields.STATUS, status);
			vo.setStatus(status);
		}
		questionVo = new QuestionImpl(vo);
		return criterias;
	}

	public Question getQuestionVo() {
		return questionVo;
	}

	public void setQuestionVo(Question questionVo) {
		this.questionVo = questionVo;
	}

	public Question getMoneyVo() {
		return questionVo;
	}

	public void setMoneyVo(Question questionVo) {
		this.questionVo = questionVo;
	}

	private int questionId;

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public int getQuestionId() {
		return questionId;
	}

	private String questionDesc;

	public void setQuestionDesc(String questionDesc) {
		this.questionDesc = questionDesc;
	}

	public String getQuestionDesc() {
		return questionDesc;
	}

	private Date questionDate;

	public void setQuestionDate(Date questionDate) {
		this.questionDate = questionDate;
	}

	public Date getQuestionDate() {
		return questionDate;
	}

	private Date consoleDate;

	public void setConsoleDate(Date consoleDate) {
		this.consoleDate = consoleDate;
	}

	public Date getConsoleDate() {
		return consoleDate;
	}

	private String answer;

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getAnswer() {
		return answer;
	}

	private int sort;

	private int status;
	private String submit;

	public void setSort(int sort) {
		this.sort = sort;
	}

	public int getSort() {
		return sort;
	}

	private int orderId;

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getOrderId() {
		return orderId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	private QuestionQueryVO questionQueryVO = new QuestionQueryVO();

	public Object getModel() {
		return questionQueryVO;
	}

	public QuestionQueryVO getQuestionQueryVO() {
		return questionQueryVO;
	}

	public void setQuestionQueryVO(QuestionQueryVO questionQueryVO) {
		this.questionQueryVO = questionQueryVO;
	}

	public String getSubmit() {
		return submit;
	}

	public void setSubmit(String submit) {
		this.submit = submit;
	}

}
