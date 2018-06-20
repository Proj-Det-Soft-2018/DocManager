package juridical.model;

import business.exception.ValidationException;
import business.model.Search;

public class JuridicalProcessSearch implements Search {
	private String number;
    private String inventorian;
    private String cpf; 
    private String lawyerName;
    private String inventoriedName;
    private int situationId;
    private int courtId;
    private int judgeId;

    @Override
    public void validate() throws ValidationException {
        boolean invalidNumber = (number == null || number.isEmpty());
        boolean invalidName = (inventorian == null || inventorian.isEmpty());
        boolean invalidCpf = (cpf == null || cpf.isEmpty());
        boolean invalidLawyer = (lawyerName == null || lawyerName.isEmpty());
        boolean invalidInventoried = (inventoriedName == null || inventoriedName.isEmpty());
        boolean invalidSituation = (situationId == 0);
        boolean invalidOrganization = (courtId == 0);
        boolean invalidSubject = (judgeId == 0);

        if(invalidNumber && invalidName && invalidCpf && invalidSituation 
            && invalidOrganization && invalidSubject && invalidLawyer && invalidInventoried) {
            throw new ValidationException("Não foram inseridos valores para busca!");
        }
    }

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @return the inventorian
	 */
	public String getInventorian() {
		return inventorian;
	}

	/**
	 * @param inventorian the inventorian to set
	 */
	public void setInventorian(String inventorian) {
		this.inventorian = inventorian;
	}

	/**
	 * @return the cpf
	 */
	public String getCpf() {
		return cpf;
	}

	/**
	 * @param cpf the cpf to set
	 */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	
	
	public String getLawyerName() {
    return lawyerName;
  }

  public void setLawyerName(String lawyerName) {
    this.lawyerName = lawyerName;
  }

  public String getInventoriedName() {
    return inventoriedName;
  }

  public void setInventoriedName(String inventoriedName) {
    this.inventoriedName = inventoriedName;
  }

  /**
	 * @return the situationId
	 */
	public int getSituationId() {
		return situationId;
	}

	/**
	 * @param situationId the situationId to set
	 */
	public void setSituationId(int situationId) {
		this.situationId = situationId;
	}

	/**
	 * @return the courtId
	 */
	public int getCourtId() {
		return courtId;
	}

	/**
	 * @param courtId the courtId to set
	 */
	public void setCourtId(int courtId) {
		this.courtId = courtId;
	}

	/**
	 * @return the judgeId
	 */
	public int getJudgeId() {
		return judgeId;
	}

	/**
	 * @param judgeId the judgeId to set
	 */
	public void setJudgeId(int judgeId) {
		this.judgeId = judgeId;
	}

    
}
