import java.util.ArrayList;

/**
 * A simple model of an auction.
 * The auction maintains a list of lots of arbitrary length.
 *
 * @author David J. Barnes and Michael Kölling.
 * @version 2011.07.31
 */
public class Auction
{
    // The list of Lots in this auction.
    private ArrayList<Lot> lots;
    // The number that will be given to the next lot entered
    // into this auction.
    private int nextLotNumber;

    /**
     * Create a new auction.
     */
    public Auction()
    {
        lots = new ArrayList<Lot>();
        nextLotNumber = 1;
    }

    /**
     * Enter a new lot into the auction.
     * @param description A description of the lot.
     */
    public void enterLot(String description)
    {
        lots.add(new Lot(nextLotNumber, description));
        nextLotNumber++;
    }

    /**
     * Show the full list of lots in this auction.
     */
    public void showLots()
    {
        for(Lot lot : lots) {
            System.out.println(lot.toString());
        }
    }

    /**
     * Make a bid for a lot.
     * A message is printed indicating whether the bid is
     * successful or not.
     * 
     * @param lotNumber The lot being bid for.
     * @param bidder The person bidding for the lot.
     * @param value  The value of the bid.
     */
    public void makeABid(int lotNumber, Person bidder, long value)
    {

        Lot selectedLot = getLot(lotNumber);
        if(selectedLot != null) {
            Bid bid = new Bid(bidder, value);
            boolean successful = selectedLot.bidFor(bid);
            if(successful) {
                System.out.println("The bid for lot number " +
                    lotNumber + " was successful.");
            }
            else {
                // Report which bid is higher.
                Bid highestBid = selectedLot.getHighestBid();
                System.out.println("Lot number: " + lotNumber +
                    " already has a bid of: " +
                    highestBid.getValue());

            }
        }

    }

    /**
     * Return the lot with the given number. Return null
     * if a lot with this number does not exist.
     * @param lotNumber The number of the lot to return.
     */
    public Lot getLot(int lotNumber)
    {
        Lot selectedLot = null;
        if((lotNumber >= 1) && (lotNumber < nextLotNumber)) {
            // The number seems to be reasonable.
            // Include a confidence check to be sure we have the
            // right lot.
            int index = 0;
            while((index < lots.size()) && (selectedLot == null)){
                if(lots.get(index).getNumber()== lotNumber){
                    selectedLot = lots.get(index);//Si existe devuelve el indice del lot
                }
                index++;
            }

        }
        if(selectedLot == null){
            System.out.println("Lote numero "+lotNumber+" no existe");

        }
        return selectedLot;

        
    }

    public void close(){
        Bid puja;
        for(Lot lot: lots){
            puja = lot.getHighestBid();
            String detalles = lot.getNumber()+" : "+ lot.getDescription();
            if(puja !=null){
                detalles = detalles +" Mayor puja"+ lot.getHighestBid().getValue() +" Pujante "+ puja.getBidder().getName();
            }

            else{
                detalles = detalles + " No hay pujas ";
            }

        }

    }

    public ArrayList<Lot> getUnsold(){

        ArrayList<Lot> sinPuja = new ArrayList<Lot>();
        for(Lot lot: lots){
            if(lot.getHighestBid() == null){
                sinPuja.add(lot);
            }
        }

        return sinPuja;
    }

    public Lot removeLot(int index){
        Lot lot = null;
        int indice = 0;
        
        while((indice < lots.size()) && (lot == null)){
            if(lots.get(indice).getNumber() == index){
                lot = lots.remove(indice);
            
            }
            indice++;
        }
        return lot;
    }
}