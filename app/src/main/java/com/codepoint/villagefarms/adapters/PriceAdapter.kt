import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.TextView

import com.codepoint.villagefarms.R
import com.codepoint.villagefarms.models.Price

import kotlin.collections.ArrayList





class PriceAdapter(private val priceList:ArrayList<Price>):RecyclerView.Adapter<PriceAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.price_individual_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return priceList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //Truncate time portion in date object; first convert to string then trim
        val created = (priceList[position].dateConfigured).toString().substring(0,10)

        holder.txtDistrict?.text = priceList[position].district
        holder.txtPricePerKg?.text = (priceList[position].pricePerKg).toString()
        holder.txtDateCreated?.text = created

            // retrieve position of list item
        val items = priceList[position]

        // Event listener used to pass data for specific item to Farmer Detail intent
        holder.itemView.setOnClickListener(View.OnClickListener { v ->


            /*val intent = Intent(v.context, FarmerMenuActivity::class.java)
            intent.putExtra("objectId", items.objectId)

            v.context.startActivity(intent)
*/
        })

    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txtDistrict = itemView.findViewById<TextView>(R.id.tvDistrict)
        val txtPricePerKg = itemView.findViewById<TextView>(R.id.tvPricePerKg)
        val txtDateCreated = itemView.findViewById<TextView>(R.id.tvDateCreated)

    }

}



