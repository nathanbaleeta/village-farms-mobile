import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.codepoint.villagefarms.R
import com.codepoint.villagefarms.models.Farmer


class FarmerAdapter(private val farmerList:ArrayList<Farmer>):RecyclerView.Adapter<FarmerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.farmer_individual_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return farmerList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.txtName?.text = farmerList[position].firstname + " " + farmerList[position].lastname
        holder?.txtLocation?.text = farmerList[position].district + ", " + farmerList[position].traditionalAuthority
        holder?.txtPhone?.text = farmerList[position].phone
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txtName = itemView.findViewById<TextView>(R.id.tvFullname)
        val txtLocation = itemView.findViewById<TextView>(R.id.tvLocation)
        val txtPhone = itemView.findViewById<TextView>(R.id.tvPhone)

    }
}