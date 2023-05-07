package modules;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Horario {
    private List<Schedule> aulas;

    public Horario() {
        this.aulas = new ArrayList<Schedule>();
    }

    public void adicionarAula(Schedule aula) {
        this.aulas.add(aula);
    }

    public void removerAula(Schedule aula) {
        this.aulas.remove(aula);
    }
/*
    public List<Schedule> pesquisarAulasPorMes(int mes) {
        List<Schedule> aulasDoMes = new ArrayList<Schedule>();
        for (Schedule aula : this.aulas) {
            if (aula.getDataAula().getMonth() == mes) {
                aulasDoMes.add(aula);
            }
        }
        return aulasDoMes;
    }
    */
    public List<Schedule> carregarAulasDeArquivoCSV(String arquivo) throws IOException {
        FileReader reader = new FileReader(arquivo);
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withDelimiter(';'));
        List<CSVRecord> linhas = csvParser.getRecords();
        boolean firstRow = true;
        for (CSVRecord linha : linhas) {
        	if (firstRow) {
				firstRow = false;
				continue; // Skip the first row
			}
            Schedule aula = new Schedule();
            aula.setCurso(linha.get(0));
            aula.setUnidadeCurricular(linha.get(1));
            aula.setTurno(linha.get(2));
            aula.setTurma(linha.get(3));
            aula.setInscritosNoTurno(linha.get(4));
            aula.setDiaSemana(linha.get(5));
            aula.setHorarioInicioAula(linha.get(6));
            aula.setHorarioFimAula(linha.get(7));
            aula.setDataAula(linha.get(8));
            aula.setSalaAtribuida(linha.get(9));
            aula.setLotacaoSala(linha.get(10));
            this.adicionarAula(aula);
        }
        reader.close();
        csvParser.close();
        return aulas;
    }
    
    public List<Schedule> getAulas(){
    	return aulas;
    }

}


