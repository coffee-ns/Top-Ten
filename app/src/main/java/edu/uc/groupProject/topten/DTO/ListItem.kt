package edu.uc.groupProject.topten.DTO

/**
 * The ListItem DTO.
 *
 * This DTO holds the name, description, and number of votes of a specific list item.
 * @param title The title of the listItem
 * @param description The description of the listItem
 * @param totalVotes The total number of votes counted towards this listItem. Handled by Firebase.
 */
data class ListItem(var title:String, var description:String="", var totalVotes:Int){

    /**
     * toString method.
     * @return title
     */
    override fun toString(): String {
        return title;
    }

}
