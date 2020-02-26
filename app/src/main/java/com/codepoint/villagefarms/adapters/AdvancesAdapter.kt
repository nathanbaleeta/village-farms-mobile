import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.TextView

import com.codepoint.villagefarms.R
import com.codepoint.villagefarms.models.Advance


class AdvancesAdapter(private val advancesList:ArrayList<Advance>):RecyclerView.Adapter<AdvancesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.advances_individual_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return advancesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtAdvanceType?.text = "Advance type: " + advancesList[position].advanceType
        holder.txtPaymentMode?.text = "Payment mode: " + advancesList[position].paymentMode
        holder.txtTotalCoffeeWeight?.text = "Total Coffee weight: " + advancesList[position].totalCoffeeWeight


        // retrieve position of list item
        val items = advancesList[position]

        // Event listener used to pass data for specific item to Farmer Detail intent
        holder.itemView.setOnClickListener(View.OnClickListener { v ->


            /*val intent = Intent(v.context, FarmerMenuActivity::class.java)
            intent.putExtra("objectId", items.objectId)
            intent.putExtra("firstName",items.firstname)
            intent.putExtra("lastName",items.lastname)
            v.context.startActivity(intent)
*/
        })

    }



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txtAdvanceType = itemView.findViewById<TextView>(R.id.tvAdvanceType)
        val txtPaymentMode = itemView.findViewById<TextView>(R.id.tvPaymentMode)
        val txtTotalCoffeeWeight = itemView.findViewById<TextView>(R.id.tvTotalCoffeeWeight)

    }

}



