package com.example.controle_aerien.services;

import com.example.controle_aerien.Djikstra.services.DjikstraImpl;
import com.example.controle_aerien.dao.AeroportRepository;
import com.example.controle_aerien.dao.VolRepository;
import com.example.controle_aerien.entities.Aeroport;
import com.example.controle_aerien.entities.Avion;
import com.example.controle_aerien.entities.Vol;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@AllArgsConstructor

public class VolService {
    @Autowired
    private VolRepository volRepository;
    @Autowired
    private AeroportService aeroportService;
    @Autowired
    DjikstraImpl djik ;
    @Autowired
    private AvionService avionService;

    public void saveVol(Vol vol)
    {
        volRepository.save(vol);
    }
    public Vol getVolById(Long id)
    {
        return volRepository.findById(id).get();
    }
    public List<Vol> getAllVol()
    {
        return volRepository.findAll();
    }
    public void deleteVolById(Long Id)
    {
        volRepository.deleteById(Id);
    }

    public Vol AddAvionToVol(Vol vol)
    {
        Avion avion = vol.getAeroportDepart().getAvionsSol().get(0);
        vol.setAvion(avion);
        avion.setDisponibilite(false);
        return (vol);
    }

    public Vol AddAeroportToVol(Long idAeroDepart ,Long idAeroArrive,Vol vol)
    {
        //calculate temps d'arrivée(update later)

        Aeroport aeroportdepart = aeroportService.getAeroportById(idAeroDepart);
        Aeroport aeroportarrive = aeroportService.getAeroportById(idAeroArrive);

        if(aeroportdepart.getAvionsSol().size()!=0 && aeroportarrive.getNbPlaceSol() > aeroportarrive.getAvionsSol().size()  && aeroportdepart.isDisponibilite() && aeroportarrive.isDisponibilite() )
        {
            vol.setAeroportDepart(aeroportdepart);
            vol.setAeroportArrivee(aeroportarrive);
            return vol;
        }
        return null;
    }

    public Vol addVol(Long idAeroDepart ,Long idAeroArrive)
    {
        Vol vol = new Vol();
        vol = AddAeroportToVol(idAeroDepart,idAeroArrive,vol);
        //temps depart affectation

        if(vol != null) {
            vol = AddAvionToVol(vol);
            return volRepository.save(vol);
        }
        return null;
    }
    public void StartVol(Long id)
    {
        Vol vol = volRepository.findById(id).get();


        HashMap<String ,Integer> dji = djik.djisktraalgo(vol.getAeroportDepart().getId(),vol.getAeroportArrivee().getId());

        if (vol != null) {
            Long aeroportDepartId = vol.getAeroportDepart().getId();
            Long aeroportArriveeId = vol.getAeroportArrivee().getId();

            // Assuming avion has x and y coordinates
            double currentX = vol.getAvion().getPosition().getX();
            double currentY = vol.getAvion().getPosition().getY();
            Integer speed = 500;
            while (!avionReachedDestination(currentX, currentY, aeroportArriveeId)) {
                // Update avion position and display progress
                speed = updateAvionPosition(vol,aeroportArriveeId,speed);
                displayProgress(vol);
                currentX=vol.getAvion().getPosition().getX();
                currentY=vol.getAvion().getPosition().getY();


                // Sleep for 1 second (1000 milliseconds)
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        System.out.println("YOUR PLANE REACHED ITS DESTINATION!!!!!!!!!!");
    }

    private void displayProgress(Vol vol) {
        // Display the avion's current position
        System.out.println("Avion Position: (" + vol.getAvion().getPosition().getX() + ", " + vol.getAvion().getPosition().getY() + ")");
    }

    private int updateAvionPosition(Vol vol, Long aeroportArriveeId, int speed) {
        Aeroport aeroportArrivee = aeroportService.getAeroportById(aeroportArriveeId);
        if (aeroportArrivee != null) {
            // Assuming a simple linear movement for demonstration purposes
            double deltaX = aeroportArrivee.getPosition().getX() - vol.getAvion().getPosition().getX();
            double deltaY = aeroportArrivee.getPosition().getY() - vol.getAvion().getPosition().getY();
            double newX;
            double newY;

            // Calculate distance and direction
            double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
            System.out.println("distance :" + distance);
            /*if(distance < 10)
            {
                 newX = vol.getAeroportArrivee().getPosition().getX();
                 newY = vol.getAeroportArrivee().getPosition().getY();
            }*/
            if(distance < 50)
            {
                speed=speed-20;
                System.out.println(speed);
            }
                double directionX = deltaX / distance;
                double directionY = deltaY / distance;

                System.out.println("direc x : " + directionX);
                System.out.println("direc y : " + directionY);

                // Calculate the distance to move based on speed (e.g., 100 km/h)
                double distanceToMove = speed / 60.0; // Convert speed to distance per second

                // Calculate the new position
                newX = vol.getAvion().getPosition().getX() + (directionX * distanceToMove);
                newY = vol.getAvion().getPosition().getY() + (directionY * distanceToMove);

            System.out.println("NEW X : " + newX);
            System.out.println("NEW Y : " +newY);

            // Update avion's position
            vol.getAvion().getPosition().setX(newX);
            vol.getAvion().getPosition().setY(newY);

        }
        return speed;
    }


    private boolean avionReachedDestination(double currentX, double currentY, Long aeroportArriveeId) {
        Aeroport aeroportArrivee = aeroportService.getAeroportById(aeroportArriveeId);
        return aeroportArrivee != null && (int) currentX == (int) aeroportArrivee.getPosition().getX() && (int) currentY == (int) aeroportArrivee.getPosition().getY();
    }


}
