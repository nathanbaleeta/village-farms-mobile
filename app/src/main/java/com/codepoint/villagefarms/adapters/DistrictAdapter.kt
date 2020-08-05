import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.*
import android.widget.TextView
import com.codepoint.villagefarms.MarketListByDistrictActivity

import com.codepoint.villagefarms.R
import com.codepoint.villagefarms.models.District

import kotlin.collections.ArrayList





class DistrictAdapter(private val districtList:ArrayList<District>):RecyclerView.Adapter<DistrictAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.district_individual_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return districtList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val created = districtList[position].created.toString().substring(0,10)

        holder.txtDistrict?.text = districtList[position].district

        //Truncate time portion in date object; first convert to string then trim
        holder.txtDateCreated?.text = created

        // retrieve position of list item
        val items = districtList[position]

        // Event listener used to pass data for specific item to Farmer Detail intent
        holder.itemView.setOnClickListener(View.OnClickListener { v ->

            val intent = Intent(v.context, MarketListByDistrictActivity::class.java)
            intent.putExtra("objectId", items.objectId)
            intent.putExtra("district",items.district)

            v.context.startActivity(intent)

        })

    }



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtDistrict = itemView.findViewById<TextView>(R.id.tvDistrict)
        val txtDateCreated = itemView.findViewById<TextView>(R.id.tvDateCreated)

    }

}



