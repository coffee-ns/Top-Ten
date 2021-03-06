package edu.uc.groupProject.topten

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import edu.uc.groupProject.topten.DTO.TestDTO
import edu.uc.groupProject.topten.DTO.ListItem
import edu.uc.groupProject.topten.Service.StrawpollService
import edu.uc.groupProject.topten.ui.main.MainFragment
import org.w3c.dom.Text

import java.util.*

/**
 * MainActivity class. Contains mocked data to test functionality.
 */
class MainActivity : AppCompatActivity() {

    /**
     * onCreate function
     * @param savedInstanceState the saved instance state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }

        this.supportActionBar?.hide();
        writeListToDatabase()
        getUser()
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    /**
     * Writes the list to the Firebase Database. Currently writes the mocked data below to the
     * database.
     */
    fun writeListToDatabase(){

        lateinit var firestore : FirebaseFirestore
        firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()

        var testList = TestDTO("Top Fifteen Movies", false, true, true, Date())


        var  listsReference = (firestore.collection("lists").document(testList.listName))


        listsReference.set(testList).addOnSuccessListener {
            Log.d("Firebase", "document saved");
        }.addOnFailureListener{
            Log.d("Firebase", "Save Failed");
        }

        var listItemCollectionReference = listsReference.collection("MyListItems")

        var arrayOfListItemsToAdd : Array<ListItem> = createSampleListItems();

        for(item in arrayOfListItemsToAdd){
            listItemCollectionReference.document(item.title).set(item);
        }

    }

    /**
     * Creates the mocked data list used by the program for testing purposes
     * @return sampleListItems
     */
    fun createSampleListItems():Array<ListItem>{

        var sampleListItems = arrayOf(
                ListItem("The Dark Knight", "A movie about Batman", 100),
                ListItem("The Return of the King", "A movie about a ring and some eagles", 150),
                ListItem("The Empire Strikes Back", "A movie about some light wands and parent issues", 200),
                ListItem("The Godfather", "n/a", 24),
                ListItem("The Avengers", "They all team up to fight bad guys", 231),
                ListItem("Inception", "", 12),
                ListItem("E.T", "", 124),
                ListItem("The Matrix", "", 42)
        )
        return sampleListItems
    }

    fun getUser(){
        lateinit var firestore : FirebaseFirestore
        firestore = FirebaseFirestore.getInstance()
        val docRef = firestore.collection("users").document("testuser")


        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("document", "DocumentSnapshot data: ${document.data}")
                    val username = findViewById<TextView>(R.id.username)
                    username.text = document.getString("username")
                    val points = findViewById<TextView>(R.id.points)
                    points.text = document.getString("points")

                } else {
                    Log.d("no document", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("error", "get failed with ", exception)
            }

    }





}