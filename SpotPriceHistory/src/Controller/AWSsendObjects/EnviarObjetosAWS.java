package Controller.AWSsendObjects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DescribeSpotPriceHistoryRequest;
import com.amazonaws.services.ec2.model.DescribeSpotPriceHistoryResult;

import Conexao.ConexaoBD;



public class EnviarObjetosAWS {
	
	public void enviarObjeto(String regiao) {

		// conecta com BD
		ConexaoBD novaConexao = new ConexaoBD();

		// recebe a conexão
		Connection con = novaConexao.conectar();

		// Objeto que prepara o código SQL
		PreparedStatement pstm = null;

		//Instancia o cliente AWS
		AmazonEC2 client = AmazonEC2ClientBuilder.standard().withRegion(regiao).build();
		DescribeSpotPriceHistoryRequest request;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {

			request = new DescribeSpotPriceHistoryRequest().withEndTime(sdf.parse("2021-10-10"))
					.withStartTime(sdf.parse("2021-10-5"));

			DescribeSpotPriceHistoryResult response = client.describeSpotPriceHistory(request);

			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

			pstm = con.prepareStatement("insert into aws (availabilityZone, instanceType,"
					+ "productDescription, spotPrice, timeStam_p) values (?, ?, ?, ?, ?)");

			System.out.println("\nEnviando Região "+regiao+" para o banco de dados");

			boolean proximo = true;

			while (proximo) {

				for (int i = 0; i < response.getSpotPriceHistory().size(); i++) {
					pstm.setString(1, response.getSpotPriceHistory().get(i).getAvailabilityZone());
					pstm.setString(2, response.getSpotPriceHistory().get(i).getInstanceType());
					pstm.setString(3, response.getSpotPriceHistory().get(i).getProductDescription());
					pstm.setString(4, response.getSpotPriceHistory().get(i).getSpotPrice());
					pstm.setString(5, dateFormat.format(response.getSpotPriceHistory().get(i).getTimestamp()));

					pstm.execute();
				}

				if (response.getSpotPriceHistory().size() < 1000) {
					proximo = false;
				}

				request.setNextToken(response.getNextToken());
				response = client.describeSpotPriceHistory(request);

			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		novaConexao.desconectar();

	}

}
