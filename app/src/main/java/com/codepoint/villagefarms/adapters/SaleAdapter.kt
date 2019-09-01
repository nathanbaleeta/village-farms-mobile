import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.codepoint.villagefarms.R
import com.codepoint.villagefarms.models.Sale

class SaleAdapter(private val saleList:ArrayList<Sale>):RecyclerView.Adapter<SaleAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.sales_individual_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return saleList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.txtName?.text = saleList[position].firstname
        holder?.txtAddress?.text = saleList[position].address
        holder?.txtPhone?.text = saleList[position].phone
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txtName = itemView.findViewById<TextView>(R.id.list_fullname)
        val txtAddress = itemView.findViewById<TextView>(R.id.list_address)
        val txtPhone = itemView.findViewById<TextView>(R.id.list_phone)

    }
}