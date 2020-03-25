package be.jkin.q2service.queue;

import be.jkin.q2service.model.Kardex;
import be.jkin.q2service.repository.KardexRepository;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class Subscriber {

    @Autowired
    private KardexRepository kardexRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(Subscriber.class);

    @RabbitListener(queues = "kardex-queue")
    public void onMessageFromRabbitMQ(final String messageFromRabbitMQ)
    {
        LOGGER.info("{}", messageFromRabbitMQ);

        JsonObject jsonObject = new JsonParser().parse(messageFromRabbitMQ).getAsJsonObject();

        String idProducto = jsonObject.get("idProducto").getAsString();
        String cantidad = jsonObject.get("cantidad").getAsString();
        String precio = jsonObject.get("precio").getAsString();
        String fecha = jsonObject.get("fecha").getAsString();

        LOGGER.info("Kudos Received: " + cantidad + " | " + precio + " | " + fecha);
        try {
            Kardex kardex = new Kardex();
            kardex.setCantidad(Integer.parseInt(cantidad));
            kardex.setPrecio(Double.parseDouble(precio));
            kardex.setIdProducto(Integer.parseInt(idProducto));
            //kardex.setFecha(Date.valueOf(fecha));

            kardexRepository.save(kardex);

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Error saving kardex", e.getMessage());
        }


    }
}
