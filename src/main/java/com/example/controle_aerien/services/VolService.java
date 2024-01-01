package com.example.controle_aerien.services;

import com.example.controle_aerien.Djikstra.services.DjikstraImpl;
import com.example.controle_aerien.dao.AeroportRepository;
import com.example.controle_aerien.dao.VolRepository;
import com.example.controle_aerien.entities.Aeroport;
import com.example.controle_aerien.entities.Avion;
import com.example.controle_aerien.entities.Vol;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
//@Transactional
@EnableScheduling

public class VolService {
    @Autowired
    private VolRepository volRepository;
    @Autowired
    private AeroportService aeroportService;
    @Autowired
    DjikstraImpl djik ;
    @Autowired
    private AvionService avionService;
    @Autowired
    private AeroportRepository aeroportRepository;
    private static final Object lockObject = new Object();
    private static final Object lockObject2 = new Object();

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
        for(Avion avion : vol.getAeroportDepart().getAvionsSol())
        {
            System.out.println("MAMAAMAMAMAAMA");
            System.out.println("AVION id =" + avion.getId() + "Avion disponibilite =" + avion.isDisponibilite());

            if(avion.isDisponibilite())
            {
                vol.setAvion(avion);
                avion.setDisponibilite(false);
                avion = avionService.saveAvion(avion);
                System.out.println("AVION id =" + avion.getId());
                return (volRepository.save(vol));
            }
        }
        return null;
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

    public Vol addVol(Long idAeroDepart, Long idAeroArrive, Vol parentVol) {
        Vol vol = new Vol();
        vol = AddAeroportToVol(idAeroDepart, idAeroArrive, vol);

        if (vol != null) {

            if (parentVol != null) {
                // If there is a parentVol, add the current vol as a subVol
                //VOL ESCALE
                vol.setAvion(parentVol.getAvion());
                parentVol.getSubVols().add(vol);
                vol.setParentVol(parentVol);
                volRepository.save(parentVol);
            }
            else {
                //VOL GLOBAL
                vol = AddAvionToVol(vol);
            }

            return volRepository.save(vol);
        }

        return null;
    }


    @Async
    public void StartVolGlobal(Vol volglobal) {
        Aeroport aeroportDepart = volglobal.getAeroportDepart();
        Aeroport aeroportArrivee = volglobal.getAeroportArrivee();

        HashMap<String, Integer> dji = djik.djisktraalgo(aeroportDepart.getId(), aeroportArrivee.getId());

        Vol voltrajet;
        int i = 0;
        Integer nextValue;

        for (Map.Entry<String, Integer> entry : dji.entrySet()) {
            if (entry.getKey().equals("T" + i)) {
                nextValue = dji.get("T" + (i + 1));

                if (nextValue != null) {
                    voltrajet = addVol(Long.valueOf(entry.getValue()), Long.valueOf(nextValue), volglobal);
                    i++;
                    StartVol(voltrajet);
                    voltrajet.setArrived(true);
                    volRepository.save(voltrajet);
                }
            }
        }
        volglobal.getAvion().setDisponibilite(true);
        volglobal.setArrived(true);
        volglobal.getAvion().setAeroport(volglobal.getAeroportArrivee());
        volglobal.getAvion().setPosition(volglobal.getAeroportArrivee().getPosition());
        for (Vol vol : volglobal.getSubVols()) {
            vol.setArrived(true);
        }
        volRepository.save(volglobal);
        avionService.saveAvion(volglobal.getAvion());

    }
    public void StartVol(Vol vol)
    {
        HashMap<String ,Integer> dji = djik.djisktraalgo(vol.getAeroportDepart().getId(),vol.getAeroportArrivee().getId());

        if (vol != null) {
            Long aeroportDepartId = vol.getAeroportDepart().getId();
            Long aeroportArriveeId = vol.getAeroportArrivee().getId();

            // Assuming avion has x and y coordinates
            double currentX = vol.getAvion().getPosition().getX();
            double currentY = vol.getAvion().getPosition().getY();
            vol.getAvion().setSpeed(400);
            Integer speed = vol.getAvion().getSpeed();
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
            while(avionReachedDestination(vol.getAvion().getPosition().getX(), vol.getAvion().getPosition().getY(), aeroportArriveeId)) {
                {
                    synchronized (lockObject2) {
                        Vol newvol = volRepository.findById(vol.getId()).get();

//                        Vol newvol = vol;
                        if(newvol.getAeroportArrivee().getAvionsSol().size() < newvol.getAeroportArrivee().getNbPlaceSol()) {
                            newvol.getAeroportArrivee().getAvionsVol().remove(newvol.getAvion());
                            aeroportService.removeAvionFromAvionsVol(newvol.getAeroportArrivee().getId(),newvol.getAvion());
                            aeroportRepository.save(newvol.getAeroportArrivee());
                            newvol.getAeroportArrivee().getAvionsSol().add(newvol.getAvion());
                            newvol.setArrived(true);
                            volRepository.save(newvol);
                            aeroportRepository.save(newvol.getAeroportArrivee());
                            vol.getAvion().setAeroport(vol.getAeroportArrivee());
                            //vol.getAvion().setPosition(vol.getAeroportArrivee().getPosition());
                            avionService.saveAvion(vol.getAvion());
                        return;
                        }
                    }
                }
            }

        }
    }

    private void displayProgress(Vol vol) {
        // Display the avion's current position
        System.out.println("Avion Position: (" + vol.getAvion().getPosition().getX() + ", " + vol.getAvion().getPosition().getY() + ")");
    }
    public synchronized int updateAvionPosition(Vol vol, Long aeroportArriveeId, int speed) {
        Aeroport aeroportArrivee = aeroportService.getAeroportById(aeroportArriveeId);
        if (aeroportArrivee != null) {
            // Assuming a simple linear movement for demonstration purposes
            double deltaXA = aeroportArrivee.getPosition().getX() - vol.getAvion().getPosition().getX();
            double deltaYA = aeroportArrivee.getPosition().getY() - vol.getAvion().getPosition().getY();
            double deltaXD = vol.getAeroportDepart().getPosition().getX() - vol.getAvion().getPosition().getX();
            double deltaYD = vol.getAeroportDepart().getPosition().getY() - vol.getAvion().getPosition().getY();
            double newX;
            double newY;

            // Calculate distanceAvionArriv and direction
            double distanceAvionArriv = Math.sqrt(deltaXA * deltaXA + deltaYA * deltaYA);
            double distanceAvionDepart = Math.sqrt(deltaXD * deltaXD + deltaYD * deltaYD);

            if(distanceAvionArriv < 50)//ATTERISSAGE
                {
                    synchronized (lockObject2) {
                        Vol newvol = volRepository.findById(vol.getId()).get();
//                        Vol newvol = vol;

                    if (newvol.getAeroportDepart().getAvionsVol().contains(newvol.getAvion())) {

                        newvol.getAvion().setAeroport(newvol.getAeroportArrivee());
                        avionService.saveAvion(newvol.getAvion());
                        vol.getAvion().setAeroport(vol.getAeroportArrivee());
                        avionService.saveAvion(vol.getAvion());

                        newvol.getAeroportDepart().getAvionsVol().remove(newvol.getAvion());
//                        aeroportService.removeAvionFromAvionsVol(newvol.getAeroportDepart().getId(), newvol.getAvion());
                        aeroportRepository.save(newvol.getAeroportDepart());

                        newvol.getAeroportArrivee().getAvionsVol().add(newvol.getAvion());
                        aeroportRepository.save(newvol.getAeroportArrivee());

                    }
                    speed = speed - 20;
                }
            }
                if (distanceAvionDepart < 50) {

                    synchronized (lockObject) {
                            Vol newvol = volRepository.findById(vol.getId()).get();
                        if (newvol.getAeroportDepart().getAvionsSol().contains(newvol.getAvion())) {
                            System.out.println("DECOLAGE--------------------------");

                            for (Avion avion : vol.getAeroportDepart().getAvionsSol()) {
                                System.out.println("AvionDS : " + avion.getId());
                            }

                            for (Avion avion : vol.getAeroportDepart().getAvionsVol()) {
                                System.out.println("AvionDV : " + avion.getId());
                            }
                            System.out.println("NEW VOL ID : " + newvol.getId());
    //                        Vol newvol = vol;
                            newvol.getAeroportDepart().getAvionsSol().remove(newvol.getAvion());
                            aeroportService.removeAvionFromAvionsSol(newvol.getAeroportDepart().getId(), newvol.getAvion());
                            //update vol by the newvol
                            aeroportRepository.save(newvol.getAeroportDepart());

                            newvol.getAeroportDepart().getAvionsVol().add(newvol.getAvion());
                            aeroportRepository.save(newvol.getAeroportDepart());
                        }
                    }

                    speed = speed + 20;
                }
                    System.out.println(speed);
                    System.out.println("distanceAvionDepart :" + distanceAvionDepart);
                    System.out.println("distanceAvionArriv :" + distanceAvionArriv);


            double directionX = deltaXA / distanceAvionArriv;
            double directionY = deltaYA / distanceAvionArriv;



            // Calculate the distanceAvionArriv to move based on speed (e.g., 400 km/h)
            double distanceAvionArrivToMove = speed / 60.0; // Convert speed to distanceAvionArriv per second


            // Calculate the new position
//            synchronized (lockObject) {
                if(distanceAvionArriv < 10)
                {
                    newX = vol.getAeroportArrivee().getPosition().getX();
                    newY = vol.getAeroportArrivee().getPosition().getY();
                }
                else {
                    newX = vol.getAvion().getPosition().getX() + (directionX * distanceAvionArrivToMove);
                    newY = vol.getAvion().getPosition().getY() + (directionY * distanceAvionArrivToMove);
                }
//            }




            // Update avion's position

            vol.getAvion().getPosition().setX((int)newX);
            vol.getAvion().getPosition().setY((int)newY);

            vol.getAvion().setSpeed(speed);
            avionService.saveAvion(vol.getAvion());
            System.out.println("distanceAvionArriv :" + distanceAvionArriv);
            System.out.println("NEW X : " + newX);
            System.out.println("NEW Y : " +newY);
            System.out.println(" Distance par second : " + distanceAvionArrivToMove + "KM/S");
            System.out.println("direc x : " + directionX);
            System.out.println("direc y : " + directionY);

        }
        return speed;
    }


    private boolean avionReachedDestination(double currentX, double currentY, Long aeroportArriveeId) {
        Aeroport aeroportArrivee = aeroportService.getAeroportById(aeroportArriveeId);
        return aeroportArrivee != null && (int) currentX == (int) aeroportArrivee.getPosition().getX() && (int) currentY == (int) aeroportArrivee.getPosition().getY();
    }

//    public void startSimulation() {
//
//    }
//    @Async
//    public void asyncStartVolGlobal(Vol vol) {
//        StartVolGlobal(vol);
//    }
}
