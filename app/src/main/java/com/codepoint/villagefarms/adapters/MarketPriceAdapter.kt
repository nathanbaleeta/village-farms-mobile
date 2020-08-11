import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.TextView

import com.codepoint.villagefarms.R
import com.codepoint.villagefarms.models.Market

import kotlin.collections.ArrayList





class MarketPriceAdapter(private val marketPriceList:ArrayList<Market>):RecyclerView.Adapter<MarketPriceAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.market_price_individual_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return marketPriceList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //Truncate time portion in date object; first convert to string then trim
        val created = (marketPriceList[position].created).toString().substring(0,10)

        holder.tvMarketName?.text = marketPriceList[position].marketName
        holder.tvMarketPrice?.text = (marketPriceList[position].marketPrice).toString()
        holder.tvDateCreated?.text = created

            // retrieve position of list item
        val items = marketPriceList[position]

        // Event listener used to pass data for specific item to Farmer Detail intent
        holder.itemView.setOnClickListener(View.OnClickListener { v ->

        })

    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvMarketName = itemView.findViewById<TextView>(R.id.tvMarketName)
        val tvMarketPrice = itemView.findViewById<TextView>(R.id.tvMarketPrice)
        val tvDateCreated = itemView.findViewById<TextView>(R.id.tvDateCreated)

    }

}



