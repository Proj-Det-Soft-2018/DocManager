package business.model;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.log4j.Logger;

import business.exception.ValidationException;

/**
 * @author lets
 *
 */
@XmlRootElement
@XmlSeeAlso(Interested.class)
public class Process {
	
	private static final Logger logger = Logger.getLogger(Process.class);
	
	private Long id;
	private boolean tipoOficio;
	private String numero;
	private Interested interessado;
	private Subject assunto;
	private Organization unidadeOrigem;
	private Situation situacao;
	private String observacao;
	private Organization unidadeDestino; //para onde o processo é dirigido quando concluido
	private LocalDateTime dataEntrada; //Hora registro do processo no banco
	private LocalDateTime dataSaida; //Hora que altera e grava situação para concluido

	public Process() {

	}

	public Process(Long id, boolean tipoOficio, String numero, String observacao) {
		this.id = id;
		this.tipoOficio = tipoOficio;
		this.numero = numero;
		this.observacao = observacao;
	}

	/**
	 * @return the id
	 */
	@XmlTransient
	public Long getId() {
		return id;
	}

	public void setId(Long processoId) {
		this.id = processoId;
	}

	/**
	 * @return the tipoOficio
	 */
	@XmlTransient
	public boolean isTipoOficio() {
		return tipoOficio;
	}

	/**
	 * @param tipoOficio the tipoOficio to set
	 */
	public void setTipoOficio(boolean tipoOficio) {
		this.tipoOficio = tipoOficio;
	}
	
	@XmlElement(name="type")
	public String getTipo () {
		return this.tipoOficio? "Ofício" : "Processo";
	}

	@XmlElement(name="number")
	public String getFormatedNumero() {
		if(this.isTipoOficio()) {
			return this.numero.replaceAll("(\\d{4})(\\d{4})(\\w)", "$1/$2-$3");
		}
		else {
			return this.numero.replaceAll("(\\d{5})(\\d{6})(\\d{4})(\\d{2})", "$1.$2/$3-$4");
		}
	}
	
	@XmlTransient
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) throws ValidationException {
		if(this.tipoOficio == true) {
			if(numero.length() < 8) {
				throw new ValidationException("O número digitado é inválido.");
			}
			else {
				if(!numero.substring(0, 7).matches("[0-9]+")) {
					throw new ValidationException("O número digitado é inválido.");
				}
			}
		}
		else {
			if(!(numero.length() == 17) || !(numero.matches("[0-9]+"))) {
				throw new ValidationException("O número digitado é inválido.");
			}
		}
		this.numero = numero;
	}
	
	@XmlElement(name="interested")
	public Interested getInteressado() {
		return interessado;
	}

	public void setInteressado(Interested interessado) {
		this.interessado = interessado;
	}

	
	@XmlElement(name="subject")
	public String getSubjectString() {
		return assunto.getText();
	}
	
	/**
	 * @return assunto
	 */
	public Subject getSubject() {
		return assunto;
	}

	/**
	 * @throws ValidationException 
	 */
	public void setAssuntoById(int idAssunto) throws ValidationException {
		if(idAssunto == 0) {
			throw new ValidationException("Campo assunto é obrigatório.");
		}
		this.assunto = Subject.getAssuntoPorId(idAssunto);
	}
	
	@XmlElement(name="origin-entity")
	public String getOriginEntityString(){
		return unidadeOrigem.getNomeExt();
	}
	
	public Organization getUnidadeOrigem() {
		return unidadeOrigem;
	}
	
	/**
	 * @throws ValidationException 
	 */
	public void setUnidadeOrigemById(int idUnidadeOrigem) throws ValidationException {
		if(idUnidadeOrigem == 0) {
			throw new ValidationException("O campo Orgão é obrigatório.");
		}
		this.unidadeOrigem = Organization.getOrgaoPorId(idUnidadeOrigem);
	}
	
	@XmlElement(name="situation")
	public String getSituationString() {
		return situacao.getStatus();
	}
	
	public Situation getSituacao() {
		return situacao;
	}

	/**
	 * @throws ValidationException 
	 */
	public void setSituacaoById(int idSituacao) throws ValidationException {
		if(idSituacao == 0) {
			throw new ValidationException("O campo Situação é obrigatório.");
		}
		this.situacao = Situation.getSituacaoPorId(idSituacao);
	}
	
	@XmlElement(name="observation")
	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	@XmlElement(name="entry-date")
	public LocalDateTime getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(LocalDateTime dataEntrada) {
		this.dataEntrada = dataEntrada;
	}
	
	public Organization getUnidadeDestino() {
		return unidadeDestino;
	}

	public void setUnidadeDestino(Organization unidadeDestino) {
		this.unidadeDestino = unidadeDestino;
	}
	
	@XmlElement(name="out")
	public LocalDateTime getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(LocalDateTime dataSaida) throws ValidationException {
		if(dataSaida.isAfter(this.dataEntrada)) {
			this.dataSaida = dataSaida;
		}
		else {
			throw new ValidationException("Verifique a Data e a Hora do seu computador.");
		}
	}
	
	public String toXml() {
		
		StringWriter stringWriter = new StringWriter();
		String xml = null;
		
		try {
			// Conversão do Objeto para um XML
			JAXBContext jaxbContext = JAXBContext.newInstance(this.getClass());
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
			jaxbMarshaller.marshal(this, stringWriter);
			
			xml = stringWriter.toString();
		} catch (JAXBException e) {
			// TODO Nova Exceção?
			logger.error(e.getMessage(), e);
		} finally {
			// Fecha o reader e o writer
			try {
				stringWriter.close();
			} catch (IOException e) {
				// Não conseguiu fechar o writer
				logger.fatal(e.getMessage(), e);
			}
		}
		
		return xml;
	}
}
