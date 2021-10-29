package Controller;

import Controller.AWSsendObjects.EnviarObjetosAWS;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DescribeRegionsResult;
import com.amazonaws.services.ec2.model.Region;


public class MenuControlador {

	public static void main(String[] args) {

		// ----------------------Start SpotPriceHistory For AWS-------------

		final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();
		DescribeRegionsResult regions_response = ec2.describeRegions();

		System.out.println("Iniciando Envio da AWS...");
		
		for (Region region : regions_response.getRegions()) {
			System.out.println("\n--------Enviando região: " + region.getRegionName()+"----------");
			EnviarObjetosAWS enviar = new EnviarObjetosAWS();
			enviar.enviarObjeto(region.getRegionName());
			System.out.println("\n--------Conteúdo Enviado----------");
		}
		
		System.out.println("Finalizado Envio da AWS...");

		// ------------------End SpotPriceHistory For AWS------------------

	}

}
