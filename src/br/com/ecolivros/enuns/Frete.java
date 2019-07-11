package br.com.ecolivros.enuns;

public enum Frete {

	AC(1, "Acre", 120),
	AL(2, "Alagoas", 115),
	AP(3, "Amapá", 110),
	AM(4, "Amazonas", 105),
	BA(5, "Bahia", 100),
	CE(6, "Ceará", 95),
	DF(7, "Distrito Federal", 40),
	ES(8, "Espírito Santo", 90),
	GO(9, "Goiás", 85),
	MA(10, "Maranhão", 80),
	MT(11, "Mato Grosso", 75),
	MS(12, "Mato Grosso do Sul", 70),
	MG(13, "Minas Gerais", 65),
	PA(14, "Pará", 60),
	PB(15, "Paraíba", 55),
	PR(16, "Paraná", 50),
	PE(17, "Pernambuco", 45),
	PI(18, "Piauí", 40),
	RJ(19, "Rio de Janeiro", 35),
	RN(20, "Rio Grande do Norte", 30),
	RS(21, "Rio Grande do Sul", 25),
	RO(22, "Rondônia", 20),
	RR(23, "Roraima", 15),
	SC(24, "Santa Catarina", 10),
	SP(25, "São Paulo", 5),
	SE(26, "Sergipe", 125),
	TO(27, "Tocantins", 130);
	
	private int codigo;
	private String descricao;
	private double valor;
	
	/**
	 * @param codigo
	 * @param descricao
	 * @param valor
	 */
	private Frete(int codigo, String descricao, double valor) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.valor = valor;
	}

	/**
	 * @return the codigo
	 */
	public int getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the valor
	 */
	public double getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(double valor) {
		this.valor = valor;
	}
}